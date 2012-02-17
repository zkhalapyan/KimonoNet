package kimononet.net.p2p;

import java.net.InetAddress;
import java.net.UnknownHostException;

import kimononet.peer.PeerAgent;

public class PeerDiscoveryService {

	/**
	 * Peer agent associated with this peer discovery service.
	 */
	private PeerAgent agent;
	
	private BeaconService beaconService;
	
	private HandshakeService handshakeService;
	
	public PeerDiscoveryService(PeerAgent agent)
	{
		this.agent = agent;
		

	}
	
	public void startService()
	{
		beaconService = new BeaconService(agent);
		beaconService.start();
		
		handshakeService = new HandshakeService(agent);		
		handshakeService.start();
	}
	
	public void shutdownService()
	{
		beaconService.shutdown();
		handshakeService.shutdown();
	}
	
	private class BeaconService extends Thread
	{
		private static final String BROADCAST_ADDRESS = "255.255.255.255";
		/** 
		 * The peer agent associated with this beacon service.
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
		
		public BeaconService(PeerAgent agent){
			this.agent = agent;
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
		
		public void run()
		{
			shutdown = false;
			
			UDPConnection connection = new UDPConnection(24242);
			BeaconPacket beacon = new BeaconPacket(agent);
			
			beacon.setType(BeaconType.BEACON);
			
			if(!connection.connect()){
				return;
			}
			
			while(true){
				
				try {
					
					byte[] packet = beacon.toByteArray();
					
					if(packet != null){
						//Send the beacon packet.
						connection.send(packet, 
										24242, 
										InetAddress.getByName(BROADCAST_ADDRESS));
					}
					
					System.out.println("Beacon sent.");
					
					//If a shutdown was requested, then exit the core loop.
					if(shutdown){
						break;
						
					//Otherwise, pause the core loop.
					}else{
						sleep(frequency);	
					}
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	private class HandshakeService extends Thread
	{
		private PeerAgent agent;
		
		/**
		 * If set to true, the core loop will exit in the next cycle.
		 */
		private boolean shutdown;
		
		public HandshakeService(PeerAgent agent)
		{
			this.agent = agent;
		}
		
		public void shutdown(){
			shutdown = true;
		}
		
		public void run()
		{
			//By default, on start, shutdown flag is off.
			shutdown = false;	
			
			while(true){
				
				if(shutdown){
					break;
				}
			}
		}
	}
	
}
