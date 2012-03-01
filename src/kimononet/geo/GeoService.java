package kimononet.geo;

import kimononet.peer.PeerAgent;

public class GeoService extends Thread {
	
	/**
	 * Peer agent associated with the current service. GPS updates will be 
	 * reflected on the peer represented by the agent.
	 */
	private PeerAgent agent;
	
	/**
	 * The sampling frequency at which the GPS enabled device will be contacted
	 * to fetch the current location.
	 */
	private int frequency;
	
	/**
	 * If set to true, the service will shut down. Use {@link #startService()} 
	 * to start the service and {@link #shutdown()} to stop the service.
	 */
	private boolean shutdown = false;
	
	/**
	 * Creates a new geo-service associated with the specified agent. Use 
	 * {@link #startService()} to start the service and {@link #shutdown()} to
	 * stop the service.
	 * 
	 * @param agent Agent associated with the current service.
	 */
	public GeoService(PeerAgent agent){
		this.agent = agent;
	}
	
	/**
	 * Starts the geo-service. Once started, a GPS enabled device will be 
	 * actively polled at {@link #frequency} rate to update peer's location. 
	 */
	public void startService(){
		this.start();
	}
	
	/**
	 * Stops the current service. To start the service use 
	 * {@link #startService()}.
	 */
	public void shutdown(){
		shutdown = true;
	}
	
	public void setFrequency(int frequency){
		this.frequency = frequency;
	
	}
	
	/**
	 * Runs the core loop - while running, a GPS enabled device will be actively 
	 * polled to get the current location and update peer's location. 
	 */
	public void run(){
		
		while(true){
			
			//Update the peer's current GPS location. By setting a new location,
			//Peer will update it's velocity - so no need to worry about that.
			agent.getPeer().setLocation(agent.getGeoDevice().getLocation());
			
			//If the service has been shutdown, it should not be started again.
			if(shutdown){
				break;
			}
			
			try {
				sleep(frequency);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			
		}
	}
}
