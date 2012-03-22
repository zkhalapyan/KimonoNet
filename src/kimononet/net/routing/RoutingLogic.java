package kimononet.net.routing;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import kimononet.geo.GeoLocation;
import kimononet.log.Logger;
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
	private boolean twoHopEnabled;
	
	public RoutingLogic(PeerAgent agent, boolean twoHopEnabled)
	{
		this.agent = agent;
		this.setTwoHopEnabled(twoHopEnabled);
	}
	
	public void updatePeerTables()
	{
		int maxRange = Integer.parseInt(agent.getEnvironment().get("max-transmission-range"));
		
		Map<PeerAddress, Peer> routingTable1 = agent.getPeers();
		for (Map.Entry<PeerAddress, Peer> entry : routingTable1.entrySet())
		{
			Peer peerN = entry.getValue();
			peerN.getLocation().move(peerN.getVelocity(), (int)(System.currentTimeMillis() / 1000));
			
			// TODO move out of range nodes to table2?
			if(peerN.getLocation().distanceTo(agent.getPeer().getLocation()) > maxRange){
				routingTable1.remove(entry.getKey());
			}
		}
		
		if(!this.twoHopEnabled)
			return;

		Map<PeerAddress, HashMap<PeerAddress, Peer>> routingTable2 = agent.getPeers2();
		for (Entry<PeerAddress, HashMap<PeerAddress, Peer>> entry : routingTable2.entrySet())
		{
			Map<PeerAddress, Peer> subTable = entry.getValue();
			for (Map.Entry<PeerAddress, Peer> subEntry : subTable.entrySet())
			{
				
				Peer peerN = subEntry.getValue();
				peerN.getLocation().move(peerN.getVelocity(), (int)(System.currentTimeMillis() / 1000));
				
				if(peerN.getLocation().distanceTo(agent.getPeer().getLocation()) < maxRange){
					subTable.remove(subEntry.getKey());
					routingTable1.put(subEntry.getKey(), subEntry.getValue());
				}
				
			}
		}
	}

	public boolean routeGreedy(DataPacket packet)
	{
		if(packet.getForwardMode() == ForwardMode.PERIMETER)
			packet.setForwardMode(ForwardMode.GREEDY);

		Map<PeerAddress, Peer> routingTable1 = agent.getPeers();
		if(routingTable1.size() == 0) {
			Logger.debug("Packet dropped because there are no peers in table to route to.");
			return false;
		}
		
		double d = packet.getDestinationPeer().getLocation().distanceTo(agent.getPeer().getLocation());
		PeerAddress id = agent.getPeer().getAddress();
		
		for (Map.Entry<PeerAddress, Peer> entry : routingTable1.entrySet())
		{
			Peer peerN = entry.getValue();
			double t = peerN.getLocation().distanceTo(packet.getDestinationPeer().getLocation());
			if (peerN.getAddress().equals(packet.getDestinationPeer().getAddress()) || t<d)
			{
				id = peerN.getAddress();
				d = t;
			}
		}
		
		if(id.equals(agent.getPeer().getAddress()))
		{
			Logger.debug("Reached local maximum, switching packet to perimeter mode");
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
			packet.initializeXHDRFields(s);
		}
		else
		{
			GeoLocation e = packet.getPerimeterEnteredLocation();
			if(d.distanceTo(s) < d.distanceTo(e))
			{
				Logger.debug("Switching packet to GREEDY mode.");
				return routeGreedy(packet);
			}
		}
		
		Map<PeerAddress, Peer> routingTable1 = agent.getPeers();
		if(routingTable1.size() == 0) {
			Logger.debug("Packet dropped because there are no peers in table to route to.");
			return false;
		}

		PeerAddress id = agent.getPeer().getAddress();
		GeoLocation x = agent.getPeer().getLocation();
		GeoLocation f = packet.getPerimeterEnteredFaceLocation();
		
		double b = d.bearingTo(f);
		
		// Make sure bearing value is within 0 and 2PI
		b = b % (2*Math.PI);
		if(b<0)
			b += (2*Math.PI);
		
		double t;
		
		for (Map.Entry<PeerAddress, Peer> entry : routingTable1.entrySet())
		{
			Peer peerN = entry.getValue();
			t = d.bearingTo(peerN.getLocation());
			
			t = t % (2*Math.PI);
			if(t<0)
				t += (2*Math.PI);
			
			if((b > 0 && t - b > 0) || (b < 0 && t - b < 0))
			{
				id = peerN.getAddress();
				
				x = peerN.getLocation();
				
				b = t;
			}
		}
		
		if(id.equals(agent.getPeer().getAddress()))
		{
			Logger.debug("No peers to route to in perimeter, dropping packet.");
			return false;
		}
		else if(packet.getForwardMode() == ForwardMode.GREEDY)
		{
			packet.setForwardMode(ForwardMode.PERIMETER);
			packet.setXHDRFaceFirstEdgeDst(x);
		}
		//TODO CHECK IF INTERSECTION FOR FULL CIRCLE CHECK

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

	/**
	 * @return twoHopEnabled boolean value for if Two Hop Routing is enabled
	 */
	public boolean isTwoHopEnabled() {
		return twoHopEnabled;
	}

	/**
	 * @param twoHopEnabled Boolean value to specify if Two Hop Routing should be enabled
	 */
	public void setTwoHopEnabled(boolean twoHopEnabled) {
		this.twoHopEnabled = twoHopEnabled;
	}
	
}
