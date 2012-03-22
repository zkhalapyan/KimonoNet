package kimononet.net.transport;

import java.util.concurrent.PriorityBlockingQueue;

import kimononet.log.LogType;
import kimononet.log.Logger;
import kimononet.net.PacketException;
import kimononet.net.p2p.Connection;
import kimononet.net.p2p.MulticastConnection;
import kimononet.net.p2p.UDPConnection;
import kimononet.net.p2p.port.PortConfiguration;
import kimononet.net.routing.RoutingLogic;
import kimononet.peer.PeerAgent;
import kimononet.service.Service;
import kimononet.stat.StatPacket;

/**
 * Service for sending and receiving data packets. 
 * 
 * @author Wade Norris
 *
 */

public class DataService extends Thread implements Service{
	
	/**
	 * Blocking priority queue is used to keep track of
	 * packets we have received and need to handle or forward.
	 */
	private PriorityBlockingQueue<DataPacket> packetQueue;
	
	/**
	 * Peer agent associated with this data service.
	 */
	private PeerAgent agent;
	
	/**
	 * Routing protocol encapsulated in RoutingLogic object for
	 * routing packets not intended for this node. 
	 */
	private RoutingLogic routingProtocol;
	
	/**
	 * If set to true, the core loop will exit in the next cycle.
	 */
	private boolean shutdown;
	
	/**
	 * If set to true the routing logic will use twoHopPeer knowledge
	 * to route packets, if set to false it will essentially just be
	 * GPSR using only knowledge of Peers in range.
	 */
	private boolean twoHopEnabled;

	Thread sendDataService = new Thread(){
		
		/**
		 * Amount of time that the data service will block for before timing out
		 * when sending outgoing packets.
		 */
		private int sendServiceTimeout;
		
		/**
		 * Connection for sending packets from the send data service.
		 */
		private Connection connection;
		
		private boolean handlePacket(DataPacket packet){
			
			// Route the packet to the next hop using routing protocol
			if(!routingProtocol.routePacket(packet)) {
				
				StatPacket p = new StatPacket(packet, agent.getPeer().getAddress());
				agent.getStatMonitor().packetDropped(p);
				
				Logger.log("Packet was dropped during routing protocol!", LogType.INFO);
				return false;
			}

			StatPacket p = new StatPacket(packet, agent.getPeer().getAddress());
			agent.getStatMonitor().packetSent(p);
			
			byte[] packetByteArray = packet.toParcel().toByteArray();
			
			if(packetByteArray != null){
				
				//Send the data packet.
				return connection.send(packetByteArray, 
									   agent.getPortConfiguration().getDataReceivingServicePort(), 
									   Connection.BROADCAST_ADDRESS);
					
			}
			
			return false;
			
		}
		
		public synchronized void run(){
			
			sendServiceTimeout = Connection.DEFAULT_TIMEOUT;
			
			//Get port and address configuration information.
			PortConfiguration conf = agent.getPortConfiguration();
			String serviceAddress = conf.getAddress();
			int servicePort = conf.getDataSendingServicePort();
			
			if(conf.isMulticast()){
				connection = new MulticastConnection(servicePort, serviceAddress);
			}else{
				connection = new UDPConnection(servicePort, serviceAddress);
			}
			
			connection.setTimeout(sendServiceTimeout);
		
			if(!connection.connect()){
				return;
			}
			
			while(!shutdown){
				
			
				while(!shutdown && packetQueue.isEmpty()) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				
				while(!packetQueue.isEmpty()){
					DataPacket topPriorityPacket = packetQueue.poll();
					handlePacket(topPriorityPacket);
				}
				
			}
			
		}
	};

	Thread receiveDataService = new Thread(){
		
		/**
		 * Amount of time that the data service will block for before timing out
		 * when listening for incoming packets.
		 */
		private int receiveServiceTimeout;
		
		public synchronized void run(){
			
			receiveServiceTimeout = Connection.DEFAULT_TIMEOUT;
			
			//Get port and address configuration information.
			PortConfiguration conf = agent.getPortConfiguration();
			String serviceAddress = conf.getAddress();
			int servicePort = conf.getDataReceivingServicePort();
			
			Connection connection;
			
			if(conf.isMulticast()){
				connection = new MulticastConnection(servicePort, serviceAddress);
			}else{
				connection = new UDPConnection(servicePort, serviceAddress);
			}
			
			connection.setTimeout(receiveServiceTimeout);
		
			if(!connection.connect()){
				return;
			}
			
			byte[] received;
			
			while(!shutdown){
				
				//Remember that receive will either timeout and return null or
				//return the received bytes.
				received = connection.receive();
				
				if(received != null){
					DataPacket packet;
					
					try{
						packet = new DataPacket(received);
					}catch(PacketException ex){
						Logger.error(ex.getMessage());
						continue;
					}
					
					//Ignore data packets from the same peer.
					if(agent.getPeer().getAddress().equals(packet.getPeer().getAddress())){
						Logger.log("Packet is addressed to self, dropped.", LogType.DEBUG);
						continue;
					}
					
					//Ignore data packets that were not intended for this agent.
					if(!agent.getPeer().getAddress().equals(packet.getHdr_fwd_dst_id())){
						Logger.log("Packet was not intended for this node, dropped.", LogType.DEBUG);
						continue;
					}
					
					StatPacket p = new StatPacket(packet, agent.getPeer().getAddress());
					agent.getStatMonitor().packetReceived(p);
					
					// Deliver packets destined for this node
					if(agent.getPeer().getAddress().equals(packet.getDestinationPeer().getAddress())){
						deliverPacket(packet);
						continue;
					}
					
					addPacketToQueue(packet);
				}
				
			}
			
		}
	};
	
	public DataService(PeerAgent agent, boolean twoHopEnabled){
		
		this.agent = agent;
		this.twoHopEnabled = twoHopEnabled;
		this.routingProtocol = new RoutingLogic(this.agent, this.twoHopEnabled);
		this.packetQueue = new PriorityBlockingQueue<DataPacket>();
		
	}

	public DataService(PeerAgent agent){
		
		this(agent, true);
		
	}
	
	private void deliverPacket(DataPacket packet){
		
		Logger.log("\nPacket delivered to destination: " + packet.toString(), LogType.INFO);
		
	}
	
	public void addPacketToQueue(DataPacket packet){
		
		packetQueue.add(packet);

		synchronized(sendDataService){
			sendDataService.notifyAll();
		}
	}
	
	public void run(){
		
		shutdown = false;
		
		sendDataService.start();
		receiveDataService.start();
	
	}
	
	
	/**
	 * Starts the data service. To shutdown the service,
	 * use {@link DataService#shutdownService()}.
	 * 
	 * @see #shutdownService()
	 */
	public void startService(){
		
		shutdown = false;
		this.start();
		
	}
	
	/**
	 * Stops the data service. To start the service,
	 * use {@link DataService#startService()}.
	 * 
	 * @see #startService()
	 */
	public void shutdownService(){
		
		shutdown = true;
		
		synchronized(sendDataService){
			sendDataService.notifyAll();
		}
		
	}
	
	/**
	 * Sets whether or not 2 Hop Routing will be enabled.
	 * @param twoHopEnabled Boolean value if true 2 Hop
	 * Routing will be enabled, if false 2 Hop Routing will
	 * be disabled.
	 */
	public void set2HopRouting(boolean twoHopEnabled){
		this.twoHopEnabled = twoHopEnabled;
		this.routingProtocol.setTwoHopEnabled(twoHopEnabled);
	}
	
}
