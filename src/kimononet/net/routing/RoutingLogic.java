package kimononet.net.routing;

import java.util.HashMap;

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
		if(routingTable1.size() == 0)
			return false;
		
		double d = packet.getDestinationPeer().getLocation().distanceTo(agent.getPeer().getLocation());
		PeerAddress id = agent.getPeer().getAddress();
		
		Peer[] peers = (Peer[]) routingTable1.values().toArray();
		
		for(int i=0; i<peers.length; i++)
		{
			double t = peers[i].getLocation().distanceTo(packet.getDestinationPeer().getLocation());
			if (t<d)
			{
				id = peers[i].getAddress();
				d = t;
			}
		}
		
		if(id.equals(agent.getPeer().getAddress()))
		{
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
			
			packet.setXHDRFields(s, s, s, d);
		}
		else
		{
			GeoLocation e = packet.getGreedyEnteredLocation();
			if(d.distanceTo(s) < d.distanceTo(e))
			{
				return routeGreedy(packet);
			}
		}
		
		HashMap<PeerAddress, Peer> routingTable1 = agent.getPeers();
		if(routingTable1.size() == 0)
			return false;

		PeerAddress id = agent.getPeer().getAddress();
		GeoLocation f = packet.getGreedyEnteredFaceLocation();
		
		//TODO calculate bearings and finish algorithm
		
		return true;
	}
	
}
