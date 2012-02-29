package kimononet.net.p2p;

import java.util.HashMap;
import java.util.zip.CRC32;

import kimononet.net.Packet;
import kimononet.net.PacketException;
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
	private static final byte SUPPORTED_VERSION = (byte) 0x01;
	
	private HashMap<PeerAddress, Peer> peers;
	
	public BeaconPacket(PeerAgent agent){
		this(agent, false);
	}
	
	/**
	 * Creates a new beacon packet associated with the specified peer agent.
	 * @param agent Peer agent to act as the source of the beacon packet.
	 */
	public BeaconPacket(PeerAgent agent, boolean ack){
		super(SUPPORTED_VERSION, PacketType.BEACON, agent.getPeer());
		
		this.peers = agent.getPeers();	
	
		setType((!ack)? PacketType.BEACON : PacketType.BEACON_ACK);
		
		setBeaconContents();
	}
	
	public BeaconPacket(byte[] data){
		this(new Parcel(data));
	}
	
	public BeaconPacket(Parcel parcel){
		super(parcel);
		parse(parcel);
	}
	
	public HashMap<PeerAddress, Peer> getPeers(){
		return peers;
	}
	
	public void parse(Parcel parcel){
		
		Parcel contents = getContents();
		
		if(contents == null){
			throw new PacketException("Malformed beacon packet. Missing contents.");
		}
		
		int numPeers = (int)parcel.getFloat();
		peers = new HashMap<PeerAddress, Peer>(numPeers);
		
		for(int i = 0; i < numPeers; i++){
			Peer peer = new Peer(parcel);
			peers.put(peer.getAddress(), peer);
		}
		
		//ToDo: CRC Check.
		//ToDo: Magic Check.
		
	}

	public Parcel toParcel(){
		
		//Get the packet parcel which includes the common header and the beacon
		//contents. Since space was allocated for a checksum, the last 4 bytes 
		//are still empty.
		Parcel packetParcel = super.toParcel();
		
		//Calculate the checksum of the current packet parcel. 
		CRC32 crc = new CRC32();
		crc.update(packetParcel.toByteArray(), 0, packetParcel.getParcelSize());
		
		//Finally add the checksum to the end of the parcel packet. This value
		//has to be copied in since the byte array contents that was copied 
		//already included the 'space' for the CRC. So if you call 
		//packetParcel.add(crc.getValue()), the parcel will run out of space and
		//throw an exception.
		packetParcel.add(packetParcel.getParcelSize() - 8, crc.getValue());
		
		return packetParcel;
	}
	
	private void setBeaconContents(){
		
		//Calculate maximum number of peers that can be included in the packet
		//content. The subtracted 12 bytes is to account for the peer count 
		//parcel variable and also the CRC that will be added at the bottom of 
		//the final Packet.
		int maxNumPeers = (int)((Connection.MAX_PACKET_LENGTH - 12) / Peer.PARCEL_SIZE);
		
		//The actual number of peers to be included in the content is the 
		//minimum of the calculated possible maximum number of peers that could
		//be included and the actual number of peers.
		int numPeers = Math.min(maxNumPeers, peers.size());
		
		//Compute the total number of bytes of a beacon parcel.
		int beaconParcelLength = numPeers * Peer.PARCEL_SIZE + 12;
		
		Parcel beaconParcel = new Parcel(beaconParcelLength);
		
		beaconParcel.add((short)numPeers);
		
		//Add all the neighbor peers to the beacon contents.
		for(PeerAddress address : peers.keySet()){
			beaconParcel.add(peers.get(address));
		}
		
		//Add a long value 0 as a temporary checksum.
		beaconParcel.add(0l);
	
		super.setContents(beaconParcel);
	}
	
	/**
	 * Returns a string representation of the current beacon packet.
	 */
	public String toString(){
		
		return super.toString();
	}
}
