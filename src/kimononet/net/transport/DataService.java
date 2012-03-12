package kimononet.net.transport;

import java.util.concurrent.PriorityBlockingQueue;

import kimononet.net.p2p.Connection;
import kimononet.net.p2p.MulticastConnection;
import kimononet.net.p2p.UDPConnection;
import kimononet.net.p2p.port.PortConfiguration;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;
import kimononet.peer.PeerAgent;

/**
 * Service for sending and receiving data packets. 
 * 
 * @author Wade Norris
 *
 */

public class DataService extends Thread{

	/**
	 * Default data service timeout in seconds.
	 */
	private static final int DEFAULT_TIMEOUT = 1;
	
	/**
	 * Default data service frequency in seconds.
	 */
	private static final int DEFAULT_FREQUENCY = 1;
	
	/**
	 * Default sleep time for the sending thread.
	 */
	private static final int DEFAULT_SLEEP = 1;
	
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
	 * If set to true, the core loop will exit in the next cycle.
	 */
	private boolean shutdown;

	Thread sendDataService = new Thread(){
		
		/**
		 * Amount of time that the data service will block for before timing out
		 * when sending outgoing packets.
		 */
		private int sendServiceTimeout;
		
		/**
		 * Amount of time the sending thread will sleep between trying to clear the
		 * packet queue.
		 */
		private int sendServiceFrequency;
		
		/**
		 * Connection for sending packets from the send data service.
		 */
		private Connection connection;
		
		private void deliverPacket(DataPacket packet){
			
			System.out.println("Packet delivered to destination!\n" + packet);
			
		}
		
		private boolean handlePacket(DataPacket packet){
			
			Peer peer = packet.getPeer();
			PeerAddress address = peer.getAddress();
			
			// Deliver packets destined for this node
			if(agent.getPeer().getAddress().equals(address)){
				deliverPacket(packet);
				return true;
			}
			else{
				//TODO send packet to routing protocol
			
				byte[] packetByteArray = packet.toParcel().toByteArray();
				
				if(packetByteArray != null){
					
					//Send the beacon packet.
					return connection.send(packetByteArray, 
										   agent.getPortConfiguration().getBeaconServicePort(), 
										   Connection.BROADCAST_ADDRESS);
				}
					
			}
			
			return false;
			
		}
		
		public void run(){
			
			sendServiceTimeout = DEFAULT_TIMEOUT;
			sendServiceFrequency = DEFAULT_SLEEP;
			
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
				
				if(!packetQueue.isEmpty()){
					DataPacket topPriorityPacket = packetQueue.poll();
					handlePacket(topPriorityPacket);
				}

				try {
					sleep(sendServiceFrequency);
				} catch (InterruptedException e) {
					e.printStackTrace();
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
		
		/**
		 * Amount of time between checks for incoming packets in data service.
		 */
		private int receiveServiceFrequency;
		
		private void addPacketToQueue(DataPacket packet){
			
			packetQueue.add(packet);
			
		}
		
		public void run(){
			
			receiveServiceTimeout = DEFAULT_TIMEOUT;
			receiveServiceFrequency = DEFAULT_SLEEP;
			
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
					
					DataPacket packet = new DataPacket(received);
					
					Peer peer = packet.getPeer();
					PeerAddress address = peer.getAddress();
					
					//Ignore data packets from the same peer.
					if(agent.getPeer().getAddress().equals(address)){
						continue;
					}
					
					//Ignore data packets that were not intended for this agent.
					if(!agent.getPeer().getAddress().equals(packet.getHdr_fwd_dst_id())){
						continue;
					}
					
					addPacketToQueue(packet);
					sendDataService.notify();
				}
				
				try {
					sleep(receiveServiceFrequency);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}
	};

	public DataService(PeerAgent agent){
		
		this.agent = agent;
		this.packetQueue = new PriorityBlockingQueue<DataPacket>();
		
	}
	
	public void run(){
		
		shutdown = false;
		
		sendDataService.start();
		receiveDataService.start();
	
	}
	
	public void shutdown(){
		
		shutdown = true;
		
	}
	
}
