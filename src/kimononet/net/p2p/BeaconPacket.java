package kimononet.net.p2p;

import java.util.HashMap;
import java.util.zip.CRC32;

import kimononet.net.Packet;
import kimononet.net.PacketType;
import kimononet.net.parcel.Parcel;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;
import kimononet.peer.PeerAgent;

/**
 * 
 * Beacon packet structure includes the following fields:
 * 
 * -neighbor count (int/4)
 * -CRC32 (byte[]/4)
 * -neighbors (neighbor count * bytes per peer)
 * 
 * @author Zorayr Khalapyan
 *
 */
public class BeaconPacket extends Packet {

	/**
	 * Current supported beacon packet version.
	 */
	private static final byte VERSION = (byte) 0x01;
	
	/**
	 * Peer agent associated with the current beacon packet.
	 */
	private PeerAgent agent;
	
	/**
	 * Creates a new beacon packet associated with the specified peer agent.
	 * @param agent Peer agent to act as the source of the beacon packet.
	 */
	public BeaconPacket(PeerAgent agent){
		super(VERSION, PacketType.BEACON, agent.getPeer());
	}
	
	public Parcel toParcel(){
		
		//Get the current peer's neighboring peers.
		HashMap<PeerAddress, Peer> peers = agent.getPeers();
		
		//Calculate maximum number of peers that can be included in the packet
		//content. The subtracted 8 bytes is to account for the peer count 
		//parcel variable and also the CRC that will be added at the bottom of 
		//the final Packet.
		int maxNumPeers = (int)((Connection.MAX_PACKET_LENGTH - 8) / Peer.PARCEL_SIZE);
		
		//The actual number of peers to be included in the content is the 
		//minimum of the calculated possible maximum number of peers that could
		//be included and the actual number of peers.
		int numPeers = Math.min(maxNumPeers, peers.size());
		
		//Compute the total number of bytes of a beacon parcel.
		int beaconParcelLength = numPeers * Peer.PARCEL_SIZE + 8;
		
		Parcel beaconParcel = new Parcel(beaconParcelLength);
		
		beaconParcel.add(numPeers);
		
		//Add all the neighbor peers to the 
		for(PeerAddress address : peers.keySet()){
			beaconParcel.add(peers.get(address));
		}
		
		super.setContents(beaconParcel.toByteArray());
		
		//Get the packet parcel which includes the common hader and the beacon
		//contents. Since space was allocated for a checksum, the last 4 bytes 
		//are still empty.
		Parcel packetParcel = super.toParcel();
		
		//Calculate the checksum of the current packet parcel. 
		CRC32 crc = new CRC32();
		crc.update(packetParcel.toByteArray(), 0, packetParcel.getParcelSize());
		
		//Finally add the checksum to the end of the parcel packet.
		packetParcel.add(crc.getValue());
		
		return packetParcel;
	}
	
	/**
	 * Returns a string representation of the current beacon packet.
	 */
	public String toString(){
		return agent.getPeer().getName() + " - " + 
			   agent.getPeer().getAddress() + " - " + 
			   agent.getPeer().getLocation();
	}
}
