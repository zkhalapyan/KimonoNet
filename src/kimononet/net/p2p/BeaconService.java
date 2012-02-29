package kimononet.net.p2p;

import java.net.InetAddress;
import java.net.UnknownHostException;

import kimononet.peer.PeerAgent;

public class BeaconService extends Thread {

	private static final int DEFAULT_FREQUENCY = 30;
	
	/**
	 * Peer agent associated with this peer discovery service.
	 */
	private PeerAgent agent;
	
	/**
	 * Beacon frequency in seconds. The variable indicates 
	 */
	private int frequency;
	
	/**
	 * If set to true, the core loop will exit in the next cycle.
	 */
	private boolean shutdown;
	
	
	public BeaconService(PeerAgent agent)
	{
		this(agent, DEFAULT_FREQUENCY);
	}
	
	public BeaconService(PeerAgent agent, int frequency){
		this.agent = agent;
		this.frequency = frequency;
	}
	
	public void run()
	{
		shutdown = false;
		
		int servicePort = agent.getPortConfiguration().getBeaconServicePort();
		Connection connection = new UDPConnection(servicePort);
		connection.setTimeout(frequency);
		
		BeaconPacket beacon = new BeaconPacket(agent);
		
		if(!connection.connect()){
			return;
		}
		
		while(true){
			
			try {
				
				byte[] packet = beacon.toParcel().toByteArray();
				
				if(packet != null){
					
					//Send the beacon packet.
					connection.send(packet, 
									servicePort, 
									InetAddress.getByName(Connection.BROADCAST_ADDRESS));
				}
				
				System.out.println("Beacon sent.");
				
				//If a shutdown was requested, then exit the core loop.
				if(shutdown){
					break;
				}
				
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sets beacon frequency.
	 * @param frequency The new beacon frequency.
	 */
	public void setFrequency(int frequency){
		this.frequency = frequency;
	}
	
	public void shutdown(){
		shutdown = true;
	}
		
}
