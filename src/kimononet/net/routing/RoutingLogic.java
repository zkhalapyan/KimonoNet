package kimononet.net.routing;

import java.util.HashMap;
import java.util.Map;

import kimononet.geo.GeoLocation;
import kimononet.net.transport.DataPacket;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;
import kimononet.peer.PeerAgent;

/**
 * Class that encapsulates routing related logic.
 *
 */
public class RoutingLogic {
	
	private PeerAgent agent;
	
	public RoutingLogic(PeerAgent agent)
	{
		this.agent = agent;
	}

	public boolean routeGreedy(DataPacket packet)
	{
		if(packet.getForwardMode() == ForwardMode.PERIMETER)
			packet.setForwardMode(ForwardMode.GREEDY);

		HashMap<PeerAddress, Peer> routingTable1 = agent.getPeers();
		if(routingTable1.size() == 0) {
			System.out.println("Packet dropped because there are no peers in table to route to!");
			return false;
		}
		
		double d = packet.getDestinationPeer().getLocation().distanceTo(agent.getPeer().getLocation());
		PeerAddress id = agent.getPeer().getAddress();
		
		for (Map.Entry<PeerAddress, Peer> entry : routingTable1.entrySet())
		{
			Peer peerN = entry.getValue();
			double t = peerN.getLocation().distanceTo(packet.getDestinationPeer().getLocation());
			if (t<d)
			{
				id = peerN.getAddress();
				d = t;
			}
		}
		
		if(id.equals(agent.getPeer().getAddress()))
		{
			System.out.println("Switching packet to perimeter mode");
			return routePerimeter(packet);
		}
		
		packet.setHdr_fwd_dst_id(id);
		
		return true;
	}
	
	public boolean routePerimeter(DataPacket packet)
	{
		
		GeoLocation d = packet.getDestinationPeer().getLocation();
		GeoLocation s = agent.getPeer().getLocation();
		
		if(packet.getForwardMode() == ForwardMode.GREEDY)
		{
			packet.setForwardMode(ForwardMode.PERIMETER);
			
			//TODO clarify which 3 or 4 should be set from protocol
			packet.setXHDRFields(s, s, s, d);
		}
		else
		{
			GeoLocation e = packet.getGreedyEnteredLocation();
			if(d.distanceTo(s) < d.distanceTo(e))
			{
				System.out.println("Switching packet to GREEDY mode");
				return routeGreedy(packet);
			}
		}
		
		HashMap<PeerAddress, Peer> routingTable1 = agent.getPeers();
		if(routingTable1.size() == 0)
			return false;

		PeerAddress id = agent.getPeer().getAddress();
		GeoLocation x;
		GeoLocation f = packet.getGreedyEnteredFaceLocation();
		
		double b = d.bearingTo(f);
		double t;
		
		for (Map.Entry<PeerAddress, Peer> entry : routingTable1.entrySet())
		{
			Peer peerN = entry.getValue();
			t = d.bearingTo(peerN.getLocation());
			
			if((b > 0 && t - b > 0) || (b < 0 && t - b < 0))
			{
				id = peerN.getAddress();
				
				x = peerN.getLocation();
				
				b = t;
			}
		}
		
		if(id.equals(agent.getPeer().getAddress()))
		{
			return false;
		}
		
		//TODO check for intersecting edges
		
		//TODO check for full circle / loops
		
		packet.setHdr_fwd_dst_id(id);
		return true;
	}
	
	public boolean routePacket(DataPacket packet)
	{
		if(packet.getForwardMode() == ForwardMode.GREEDY) {
			if(!routeGreedy(packet)) {
				return false;
			}
		}
		else {
			if(!routePerimeter(packet)) {
				return false;
			}
		}
		return true;
	}
	
}
