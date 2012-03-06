package kimononet.net.beacon;

import java.util.HashMap;

import kimononet.net.p2p.Connection;
import kimononet.net.p2p.MulticastConnection;
import kimononet.net.p2p.UDPConnection;
import kimononet.net.p2p.port.PortConfiguration;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;
import kimononet.peer.PeerAgent;

/**
 * Service for sending and receiving beacon packets. 
 * 
 * @author Zorayr Khalapyan
 *
 */
public class BeaconService extends Thread {

	private static final int DEFAULT_FREQUENCY = 30;
	
	/**
	 * Peer agent associated with this peer discovery service.
	 */
	private PeerAgent agent;
	
	/**
	 * Beacon frequency in seconds. The variable indicates 
	 */
	private int frequency;
	
	/**
	 * If set to true, the core loop will exit in the next cycle.
	 */
	private boolean shutdown;
	
	
	public BeaconService(PeerAgent agent)
	{
		this(agent, DEFAULT_FREQUENCY);
	}
	
	public BeaconService(PeerAgent agent, int frequency){
		this.agent = agent;
		this.frequency = frequency;
	}
	
	public void run()
	{
		shutdown = false;
		
		//Get port and address configuration information.
		PortConfiguration conf = agent.getPortConfiguration();
		String serviceAddress = conf.getAddress();
		int servicePort = conf.getBeaconServicePort();
		
		Connection connection;
		
		if(conf.isMulticast()){
			connection = new MulticastConnection(servicePort, serviceAddress);
		}else{
			connection = new UDPConnection(servicePort, serviceAddress);
		}
		
		connection.setTimeout(frequency);
	
		if(!connection.connect()){
			return;
		}
		
		byte[] received;
		
		while(true){
			
			//Remember that receive will either timeout and return null or
			//return the received bytes.
			received = connection.receive();
			
			if(received == null){
				sendBeacon(connection);
					
			}else{
				
				BeaconPacket packet = new BeaconPacket(received);
				
				Peer peer = packet.getPeer();
				PeerAddress address = peer.getAddress();
			 
					
				//Ignore beacons from the same peer.
				if(agent.getPeer().getAddress().equals(address)){
					continue;
				} 
				
				//Add new peers or replace peers with updated locations.
				updatePeer(agent.getPeers(), peer);
				
				//This is the neighbor's neighbors table. Don't confuse this 
				//with the peers2 table inside the agent - that's like one layer
				//up, where a peer address is mapped to a <address, peer> table.
				HashMap<PeerAddress, Peer> peers2;
				
				//If the peers2 table doesn't have an entry from the beacon's
				//source, then create one.
				if(!agent.getPeers2().containsKey(address)){
					peers2 = new HashMap<PeerAddress, Peer>(packet.getPeers().size());
					agent.getPeers2().put(address, peers2);
				}else{
					peers2 = agent.getPeers2().get(address);
				}
					
				for(PeerAddress neighborAddress : packet.getPeers().keySet()){
					updatePeer(peers2, packet.getPeers().get(neighborAddress));
				}
				
				//If the source of the beacon doesn't know about us, then
				//send a beacon as a response.
				if(packet.getPeers().containsKey(agent.getPeer().getAddress())){
					sendBeacon(connection);
				}
				
			
				
				
			}
			
			
			
			//If a shutdown was requested, then exit the core loop.
			if(shutdown){
				break;
			}
			
		
		}
	}
	
	private void updatePeer(HashMap<PeerAddress, Peer> peers, Peer peer){
		
		PeerAddress address = peer.getAddress();
		int updateTime = peer.getLocation().getTimestamp();
		
		if(!peers.containsKey(address) 
	      || peers.get(address).getLocation().getTimestamp() > updateTime){
			peers.put(address, peer);
		}
	}
	
	/**
	 * Broadcasts a beacon packet associated with the current agent.
	 * 
	 * @param connection Connection to use to send a packet.
	 * @return True on success; false, otherwise.
	 */
	private boolean sendBeacon(Connection connection){
			
		BeaconPacket beacon = new BeaconPacket(agent);
	
		byte[] packet = beacon.toParcel().toByteArray();
		
		if(packet != null){
			
			//Send the beacon packet.
			return connection.send(packet, 
								   agent.getPortConfiguration().getBeaconServicePort(), 
								   Connection.BROADCAST_ADDRESS);
		}
			
		
		
		return false;
	}
	
	/**
	 * Sets beacon frequency.
	 * @param frequency The new beacon frequency.
	 */
	public void setFrequency(int frequency){
		this.frequency = frequency;
	}
	
	public void shutdown(){
		shutdown = true;
	}
		
}
