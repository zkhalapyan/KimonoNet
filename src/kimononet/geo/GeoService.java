package kimononet.geo;

import kimononet.peer.PeerAgent;

public class GeoService extends Thread {
	
	private PeerAgent agent;
	
	private int frequency;
	
	private boolean shutdown = false;
	
	public GeoService(PeerAgent agent){
		this.agent = agent;
	}
	
	/**
	 * Once stopped, the service shut not be started again.
	 */
	public void shutdown(){
		shutdown = true;
	}
	
	public void run(){
		
		while(true){
			
			//If the service has been shutdown, it should not be started again.
			if(shutdown){
				break;
			}
			
			
			
		}
	}
}
