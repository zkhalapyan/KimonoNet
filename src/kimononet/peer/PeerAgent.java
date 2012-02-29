package kimononet.peer;

import java.util.HashMap;

import kimononet.geo.DefaultGeoDevice;
import kimononet.geo.GeoDevice;
import kimononet.geo.GeoService;
import kimononet.net.p2p.BeaconService;
import kimononet.net.p2p.port.PortConfiguration;
import kimononet.net.p2p.port.PortConfigurationProvider;
import kimononet.net.p2p.port.SimulationPortConfigurationProvider;

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
	 * Peer discovery service associated with the current peer. Peer discovery
	 * service is responsible for sending beacons and beacon acknowledgements.
	 */
	private BeaconService beaconService;
	
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
	 * Port configuration provider for the current peer. Services that need to 
	 * create socket connections for various services will consult the provider
	 * for configuring the correct port numbers. 
	 */
	private PortConfigurationProvider portConfigurationProvider;
	
	/**
	 * Neighboring peer's mapped to a peer address. This is ROUTING_TABLE_1 
	 * described in the protocol documentation.
	 */
	private HashMap<PeerAddress, Peer> peers;
	
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
		
		this(peer, 
		     environment, 
		     geoDevice, 
		     new SimulationPortConfigurationProvider());
	}
	
	/**
	 * Creates a peer agent associated with the specified peer environment.
	 * 
	 * @param peer The peer represented by the peer agent.
	 * @param environment The peer's environment.
	 * @param geoDevice The peer's GPS device.
	 * @param portConfigurationProvider Port configuration provider.
	 */
	public PeerAgent(Peer peer, 
					 PeerEnvironment environment, 
					 GeoDevice geoDevice, 
					 PortConfigurationProvider portConfigurationProvider){
		
		this.peer                      = peer;
		this.environment               = environment;
		this.geoDevice                 = geoDevice;
		this.portConfigurationProvider = portConfigurationProvider;
		
		this.beaconService = new BeaconService(this);		
		this.geoService    = new GeoService(this);
		
		this.peers = new HashMap<PeerAddress, Peer>();
	}
	
	
	public void startServices(){
		this.geoService.startService();
		this.beaconService.start();
		
	}
	
	public void shutdownServices(){
		this.geoService.shutdown();
		this.beaconService.shutdown();
	}
	
	/**
	 * Returns the current peer's peers.
	 * @return The current peer's peers.
	 */
	public HashMap<PeerAddress, Peer> getPeers(){
		return this.peers;
	}
	
	/**
	 * Returns current peer's port configuration. 
	 * @return Peer's port configuration. 
	 */
	public PortConfiguration getPortConfiguration(){
		return this.portConfigurationProvider.getPortConfiguration(this);
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
