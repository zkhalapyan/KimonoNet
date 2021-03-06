package kimononet.net.beacon;

import java.util.HashMap;
import java.util.Map;

import kimononet.log.LogType;
import kimononet.log.Logger;
import kimononet.net.PacketException;
import kimononet.net.p2p.Connection;
import kimononet.net.p2p.MulticastConnection;
import kimononet.net.p2p.UDPConnection;
import kimononet.net.p2p.port.PortConfiguration;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;
import kimononet.peer.PeerAgent;
import kimononet.service.Service;
import kimononet.stat.StatPacket;

/**
 * Service for sending and receiving beacon packets. 
 * 
 * @author Zorayr Khalapyan
 * @version 3/18/2012
 *
 */
public class BeaconService extends Thread implements Service{

	/**
	 * Default timeout for sending beacon packets. This value dictates how 
	 * long in seconds a beacon service waits for receiving a beacon from a 
	 * neighbor before sending out its own beacon.
	 */
	private static final int DEFAULT_TIMEOUT = 5000;
	
	/**
	 * Peer agent associated with this peer discovery service.
	 */
	private PeerAgent agent;	
	
	/**
	 * This value indicates how long in seconds a beacon service waits for 
	 * receiving a beacon from a neighbor before sending out its own beacon.
	 */
	private int timeout;
	
	/**
	 * If set to true, the core loop will exit in the next cycle.
	 */
	private boolean shutdown;
	
	/**
	 * If set, the beacon service will ignore all packets outside of this range.
	 * The value is specified in meters.
	 */
	private Integer maxRange;
	
	/**
	 * Creates a beacon service associated with the specified peer agent with
	 * a default timeout value indicated by {@link #DEFAULT_TIMEOUT}.
	 * 
	 * @param agent Agent representing the peer associated with the sent 
	 * 				beacons.
	 */
	public BeaconService(PeerAgent agent){
		this(agent, DEFAULT_TIMEOUT);
	}
	
	/**
	 * Creates a new beacon service associated with the specified peer agent and 
	 * with a set frequency for sending out beacon packets.
	 * 
	 * @param agent Agent representing the peer associated with the sent 
	 * 				beacons.
	 * 
	 * @param timeout This value indicates how long in seconds a 
	 * 				  beacon service waits for receiving a beacon from a 
	 * 				  neighbor before sending out its own beacon.
	 */
	public BeaconService(PeerAgent agent, int timeout){
		this.agent   = agent;
		this.timeout = timeout;
		
		//If the value is not set within the environment, then maxRnage will be
		//set to null.
		//ToDo: Remove association between beacon service and environment variables.
		maxRange = Integer.parseInt(agent.getEnvironment().get("max-transmission-range"));
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
		
		float randomAdditive = 0;
		
		if(agent.getEnvironment().get("beacon-service-timeout-random-additive") != null)
			randomAdditive = Float.parseFloat(agent.getEnvironment().get("beacon-service-timeout-random-additive"));
		
		connection.setTimeout(timeout + (int)(Math.random() * timeout * randomAdditive));
	
		if(!connection.connect()){
			return;
		}
		
		byte[] received;
		
		while(!shutdown){
			
			//Remember that receive will either timeout and return null or
			//return the received bytes.
			received = connection.receive();
			
			if(received == null){
				Logger.log("Beacon service for " + agent.getPeer().getAddress() + " timed out. Sending a beacon.", LogType.DEBUG);
				sendBeacon(connection);
					
			}else{
				BeaconPacket packet;
				
				try{
					//Parse the received byte array to a beacon packet.
					packet = new BeaconPacket(received);
					 
				//In case of an error, output the message and reset the loop.
				}catch(PacketException ex){
					Logger.log(ex.getMessage(), LogType.ERROR);
					continue;
				}
				
				Peer peer = packet.getPeer();
				PeerAddress address = peer.getAddress();
					
				//Ignore beacons from the same peer.
				if(agent.getPeer().getAddress().equals(address)){
					continue;
				} 
				
				//If the peer is out of range, than ignore the packet.
				if(maxRange != null 
				   && agent.getPeer().getLocation().distanceTo(packet.getPeer().getLocation()) > maxRange) {
					continue;
				}
				
				//If the agent has a stat monitor, notify that a packet has been
				//received.
				if(agent.getStatMonitor() != null){
					agent.getStatMonitor().packetReceived(new StatPacket(packet));
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
					
					//It is redundant to put the current peer inside peers2.
					if(!neighborAddress.equals(agent.getPeer().getAddress())){
						updatePeer(peers2, packet.getPeers().get(neighborAddress));
					}
				}
				
				//If the source of the beacon doesn't know about us, then
				//send a beacon as a response.
				if(!packet.getPeers().containsKey(agent.getPeer().getAddress())){
					sendBeacon(connection);
					Logger.debug("Apparently peer " + packet.getPeer().getAddress() + " doesn't know about " + agent.getPeer().getAddress());
				}
				
				
			}
			
		
		}
	}
	
	private void updatePeer(Map<PeerAddress, Peer> peers, Peer peer){
		
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
			boolean success = connection.send(packet, 
								              agent.getPortConfiguration().getBeaconServicePort(), 
								              Connection.BROADCAST_ADDRESS);
			
			//If the agent has a stat monitor and the packet was successfully 
			//sent, notify the stat monitor.
			if(agent.getStatMonitor() != null && success){
				agent.getStatMonitor().packetSent(new StatPacket(beacon));
				
				Logger.debug("SENT BEACON PACKET.");
			}

			return success;
		}
			
		return false;
	}
	
	/**
	 * Sets beacon timeout value.
	 * @param timeout The new beacon service timeout value.
	 * @see #timeout
	 * @see #DEFAULT_TIMEOUT
	 */
	public void setTimeout(int timeout){
		this.timeout = timeout;
	}
	
	@Override
	public void shutdownService(){
		shutdown = true;
	}
	
	@Override
	public void startService(){
		shutdown = false;
		
		this.start();
	}
		
}
