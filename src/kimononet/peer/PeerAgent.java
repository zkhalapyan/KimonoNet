package kimononet.peer;

import kimononet.geo.DefaultGeoDevice;
import kimononet.geo.GeoDevice;
import kimononet.geo.GeoService;
import kimononet.net.p2p.PeerDiscoveryService;

/**
 * 
 * @author Zorayr Khalapyan
 *
 */
public class PeerAgent {

	/**
	 * Current peer represented by this agent.
	 */
	private Peer peer;
	
	/**
	 * Peer discovery service associated with the current peer.
	 */
	private PeerDiscoveryService discoveryService;
	
	/**
	 * Stores current peer's environment information.
	 */
	private PeerEnvironment environment;
	
	/**
	 * Peer's GPS device.
	 */
	private GeoDevice geoDevice;
	
	/**
	 * Service that update's the current peer's GPS location at a certain
	 * frequency.
	 */
	private GeoService geoService;
	
	/**
	 * Creates a peer agent with a default peer environment.
	 * 
	 * @param peer The peer represented by the peer agent.
	 */
	public PeerAgent(Peer peer){
		this(peer, new DefaultPeerEnvironment());
	}
	
	/**
	 * Creates a peer agent associated with the specified peer environment.
	 * 
	 * @param peer The peer represented by the peer agent.
	 * @param environment The peer's environment.
	 */
	public PeerAgent(Peer peer, PeerEnvironment environment){
		this(peer, environment, new DefaultGeoDevice());
	}
	
	/**
	 * Creates a peer agent associated with the specified peer environment.
	 * 
	 * @param peer The peer represented by the peer agent.
	 * @param environment The peer's environment.
	 * @param geoDevice The peer's GPS device.
	 */
	public PeerAgent(Peer peer, PeerEnvironment environment, GeoDevice geoDevice){
		this.peer = peer;
		this.environment = environment;
		this.geoDevice = geoDevice;
		
		this.discoveryService = new PeerDiscoveryService(this);		
		this.geoService = new GeoService(this);
	}
	
	public void startServices(){
		this.geoService.startService();
		this.discoveryService.startService();
		
	}
	
	public void shutdownServices(){
		this.geoService.shutdown();
		this.discoveryService.shutdownService();
	}
	
	/**
	 * Returns the peer's GPS device.
	 * @return The peer's GPS device.
	 */
	public GeoDevice getGeoDevice(){
		return geoDevice;
	}
	
	/**
	 * Returns the peer represented by this agent.
	 * @return The peer represented by this agent.
	 */
	public Peer getPeer() {
		return peer;
	}

	/**
	 * Returns the peer's environment.
	 * @return the peer's environment.
	 */
	public PeerEnvironment getEnvironment() {
		return environment;
	}

	/**
	 * Sets the peer's environment. 
	 * @param environment Peer's new environment.
	 */
	public void setEnvironment(PeerEnvironment environment) {
		this.environment = environment;
	}
}
