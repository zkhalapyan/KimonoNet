package kimononet.net.p2p.port;

import kimononet.peer.PeerAgent;


public class SimulationPortConfigurationProvider 
	   				implements PortConfigurationProvider {

	
	/**
	 * Default local source address during simulation mode.
	 */
	private String ADDRESS = "235.1.1.1"; 
	
	/**
	 * 
	 */
	private boolean IS_MULTICAST = true;
	
	private static final int BEACON_SERVICE_PORT = 55461;
	
	private static final int DATA_SENDING_SERVICE_PORT = 55462;
	
	private static final int DATA_RECEIVING_SERVICE_PORT = 55463;
	
	private static PortConfiguration portConfiguration;
	
	public SimulationPortConfigurationProvider(){
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
