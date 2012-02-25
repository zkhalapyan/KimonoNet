package kimononet.net.p2p.port;

import java.util.HashMap;

import kimononet.peer.PeerAddress;
import kimononet.peer.PeerAgent;


public class SimulationPortConfigurationProvider 
	   				implements PortConfigurationProvider {

	
	private static final int INITIAL_PORT_NUMBER = 5000;
	
	private int currentPortNumber;
	
	private HashMap<PeerAddress, PortConfiguration> portMap;
	
	public SimulationPortConfigurationProvider(){
		currentPortNumber = INITIAL_PORT_NUMBER;
	}
	
	@Override
	public PortConfiguration getPortConfiguration(PeerAgent agent) {

		PeerAddress address = agent.getPeer().getAddress();
		
		if(!portMap.containsKey(address)){
			portMap.put(address, new PortConfiguration(currentPortNumber++,
				       currentPortNumber++,
					   currentPortNumber++));
		}
		
		return portMap.get(address);
	}



}
