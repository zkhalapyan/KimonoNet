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
	 * Amount of time that the data service will block for before timing out
	 * when listening for incoming packets.
	 */
	private int timeout;
	
	/**
	 * Amount of time between checks for incoming packets in data service.
	 */
	private int frequency;
	
	/**
	 * Amount of time the sending thread will sleep between trying to clear the
	 * packet queue.
	 */
	private int sleepTime;
	
	/**
	 * If set to true, the core loop will exit in the next cycle.
	 */
	private boolean shutdown;

	Thread sendDataService = new Thread(){
		
		public void run(){
			
			while(!shutdown)
			{
				if(packetQueue.isEmpty())
				{
					try {
						sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				
			}
			
		}
	};

	Thread receiveDataService = new Thread(){
		
		public void run(){
			
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
			
			connection.setTimeout(timeout);
		
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
					
					try {
						sleep(frequency);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		}
	};
	
	public DataService(PeerAgent agent)
	{
		this(agent, DEFAULT_FREQUENCY);
	}
	
	private void addPacketToQueue(DataPacket packet) {
		
		packetQueue.add(packet);
		
	}

	public DataService(PeerAgent agent, int frequency){
		this.agent = agent;
		this.timeout = DEFAULT_TIMEOUT;
		this.sleepTime = DEFAULT_SLEEP;
		this.frequency = frequency;
		this.packetQueue = new PriorityBlockingQueue<DataPacket>();
	}
	
	public void run()
	{
		
		shutdown = false;
		
		sendDataService.start();
		receiveDataService.start();
	
	}
	
	public void shutdown()
	{
		shutdown = true;
	}
	
}
