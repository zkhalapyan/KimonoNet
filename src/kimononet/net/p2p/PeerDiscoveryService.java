package kimononet.net.p2p;

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
	
	public void start()
	{
		beaconService = new BeaconService(agent);
		beaconService.start();
		
		handshakeService = new HandshakeService(agent);		
		handshakeService.start();
	}
	
	public void shutdown()
	{
		beaconService.shutdown();
		handshakeService.shutdown();
	}
	
	private class BeaconService extends Thread
	{
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
			
			while(true){
				
				try {
					
					
					//If a shutdown was requested, then exist the core loop.
					if(shutdown){
						break;
						
					//Otherwise, pause the core loop 
					}else{
						sleep(frequency);	
					}
					
					
				} catch (InterruptedException e) {
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
