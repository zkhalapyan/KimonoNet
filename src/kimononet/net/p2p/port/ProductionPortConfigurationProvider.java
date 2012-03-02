package kimononet.net.p2p.port;

import kimononet.peer.PeerAgent;


public class ProductionPortConfigurationProvider 
	   				implements PortConfigurationProvider {

	
	/**
	 * Default local source address during simulation mode.
	 * ToDo: Determine current NIC's IP address instead of hard coding an
	 * address. 
	 */
	private String ADDRESS = "235.1.1.1"; 
	
	/**
	 * Indicates whether the connection should use multicast protocol as opposed
	 * to a simple UDP connection.
	 */
	private boolean IS_MULTICAST = false;
	
	private static final int BEACON_SERVICE_PORT = 5000;
	
	private static final int DATA_SENDING_SERVICE_PORT = 5001;
	
	private static final int DATA_RECEIVING_SERVICE_PORT = 5002;
	
	private static PortConfiguration portConfiguration;
	
	public ProductionPortConfigurationProvider(){
		portConfiguration = new PortConfiguration(ADDRESS,
				 								  BEACON_SERVICE_PORT,
				 								  DATA_SENDING_SERVICE_PORT,
				 								  DATA_RECEIVING_SERVICE_PORT,
				 								  IS_MULTICAST);
	}
	
	@Override
	public PortConfiguration getPortConfiguration(PeerAgent agent) {
		return portConfiguration;
	}



}
