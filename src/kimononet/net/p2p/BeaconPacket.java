package kimononet.net.p2p;

import kimononet.peer.PeerAgent;

public class BeaconPacket {

	private PeerAgent agent;
	
	public BeaconPacket(PeerAgent agent){
	
		this.agent = agent;
	}
	
	public byte[] toByteArray(){
		return null;
	}
	
	public String toString(){
		return agent.getPeer().getName() + " - " + 
			   agent.getPeer().getAddress() + " - " + 
			   agent.getPeer().getLocation();
	}
}
