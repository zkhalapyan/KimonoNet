package kimononet.geo;

import kimononet.peer.PeerAgent;

public class GeoService extends Thread {
	
	private PeerAgent agent;
	
	private int frequency;
	
	private boolean shutdown = false;
	
	public GeoService(PeerAgent agent){
		this.agent = agent;
	}
	
	public void startService(){
		this.start();
	}
	
	/**
	 * Once stopped, the service shut not be started again.
	 */
	public void shutdown(){
		shutdown = true;
	}
	
	public void run(){
		
		while(true){
			
			//Update the peer's current GPS location.
			agent.getPeer().setLocation(agent.getGeoDevice().getLocation());
			
			//If the service has been shutdown, it should not be started again.
			if(shutdown){
				break;
			}			
			
		}
	}
}
