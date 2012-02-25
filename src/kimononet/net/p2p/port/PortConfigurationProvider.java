package kimononet.net.p2p.port;

import kimononet.peer.PeerAgent;

public interface PortConfigurationProvider {

	public PortConfiguration getPortConfiguration(PeerAgent agent);
}
