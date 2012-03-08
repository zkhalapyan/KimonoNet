package kimononet.net.transport;

import java.util.zip.CRC32;

import kimononet.geo.GeoLocation;
import kimononet.net.Packet;
import kimononet.net.PacketException;
import kimononet.net.PacketType;
import kimononet.net.parcel.Parcel;
import kimononet.net.routing.ForwardMode;
import kimononet.net.routing.QualityOfService;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;
import kimononet.peer.PeerAgent;

/**
 *  * A subclass of Packet that represents a data packet.
 * 
 * @author Wade Norris
 * 
 */
public class DataPacket extends Packet {

	/**
	 * Currently supported data packet version.
	 */
	private static final byte SUPPORTED_VERSION = (byte) 0x01;
	
	/**
	 * Size of Main Data Packet Header in bytes.
	 */
	private static final short DATA_HDR_SIZE = 48;
	
	/**
	 * Size of Extended Data Packet Header in bytes.
	 */
	private static final short DATA_HDR_EXT_SIZE = 96;
	
	
	/**
	 * Holds the peer that this packet is being sent to.
	 */
	private Peer destinationPeer;

	/**
	 * Holds data payload of this data packet.
	 */
	private byte[] payload;
	
	
	/**
	 * Creates a new data packet associated with the specified peer agent.
	 * @param agent Peer agent to act as the source of the data packet.
	 * @param peer Peer to act as the destination of the data packet.
	 * @param payload Byte data that is the data to be sent in this packet.
	 */
	public DataPacket(PeerAgent agent, Peer peer, byte[] payload){
		super(SUPPORTED_VERSION, PacketType.DATA, agent.getPeer());
		
		this.destinationPeer = peer;
		this.payload = payload;
		
		setDataPacketContents();
	}

	/**
	 * Creates a new data packet from the provided byte array.
	 * 
	 * @param data Byte array representation of a data packet.
	 */
	public DataPacket(byte[] data){
		this(new Parcel(data));
	}
	
	/**
	 * Creates a new data packet from the provided parcel.
	 * 
	 * @param parcel Parcel representation of a data packet.
	 * @see {@link #parse(Parcel)}
	 */
	public DataPacket(Parcel parcel){
		parse(parcel);
	}
	
	public void parse(Parcel parcel){
		
		super.parse(parcel);
		
		Parcel contents = getContents();
			
		if(contents == null){
			throw new PacketException("Malformed data packet. Missing contents.");
		}
		
		// HDR-DST-ID (8)
		PeerAddress address = new PeerAddress(parcel);
		
		// HDR-DST-LOC (24)
		GeoLocation geolocation = new GeoLocation(parcel);
		
		this.destinationPeer = new Peer(address);
		this.destinationPeer.setLocation(geolocation);
		
		// HDR-FWD-DST-ID (8)
		PeerAddress nexthop = new PeerAddress(parcel);
		
		// HDR-FWD-MODE (1)
		byte fwdflag = parcel.getByte();
		
		// HDR-DATA-LEN (2)
		short dataParcelLength = parcel.getShort();
		
		// HDR-QOS (1)
		byte qosflag = parcel.getByte();
		
		// HDR-HDR-CHK (4)
		int checksum = parcel.getInt();
		
		// XHDR (96)
		//ToDo: Extract extended header
		
		//ToDo: Extract payload
		
		//ToDo: CRC Check.
		//ToDo: Magic Check.
		
	}

	public Parcel toParcel(){
		
		Parcel packetParcel = super.toParcel();
		
		// Function has yet to be implemented.
		
		return packetParcel;
	}

	/**
	 * Sets up the content of a Data Packet
	 */
	private void setDataPacketContents() {
		
		short payloadLength = (short) this.payload.length;
		short dataParcelLength = (short) (DATA_HDR_SIZE + DATA_HDR_EXT_SIZE + payloadLength);
		
		Parcel dataParcel = new Parcel(dataParcelLength);
		
		// HDR-DST-ID (8)
		dataParcel.add(this.destinationPeer.getAddress());
		
		// HDR-DST-LOC (24)
		dataParcel.add(this.destinationPeer.getLocation());
		
		// HDR-FWD-DST-ID (8)
		// Next Hop ID initially set to 0
		dataParcel.add(((long)0x0));
		
		// HDR-FWD-MODE (1)
		dataParcel.add(ForwardMode.GREEDY.getFlag());
		
		// HDR-DATA-LEN (2)
		dataParcel.add(payloadLength);
		
		// HDR-QOS (1)
		// Quality of Service set to MEDIUM
		dataParcel.add(QualityOfService.MEDIUM.getFlag());
		
		// HDR-HDR-CHK (4)
		// Checksum initially set to 0
		dataParcel.add(((int)0x0));
		
		// XHDR (96)
		// Set Extended Header initially to 0
		for(int i=0; i<DATA_HDR_EXT_SIZE; i++) {
			dataParcel.add(((byte)0x0));
		}
		
		// Add data payload
		dataParcel.add(this.payload);
		
		super.setContents(dataParcel);
		
	}

	/**
	 * Getter for destination peer for this data packet.
	 * @return Peer Destination peer for this packet
	 */
	public Peer getDestinationPeer() {
		return destinationPeer;
	}

	/**
	 * Getter for payload of this data packet
	 * @return byte[] Payload for this data packet
	 */
	public byte[] getPayload() {
		return payload;
	}
	
	/**
	 * Returns a string representation of the current data packet.
	 */
	public String toString(){
		
		return super.toString() + 
				   "\n--------------------------------------------- \n" +
				   "Data Packet Header\n" +  
				   "HDR-DST-ID:  \t" + this.destinationPeer.getAddress() + "\n" + 
				   "HDR-DST-LOC: \t" + this.destinationPeer.getLocation() + "\n" +
				   "HDR-FWD-DST-ID: \t" + "TODO" + "\n" + 
				   "HDR-FWD-MODE:   \t" + "TODO" + "\n" + 
				   "HDR-DATA-LEN: \t" + this.payload.length + "\n" + 
				   "HDR-QOS:    \t" + "TODO" + "\n" +  
				   "HDR-HDR-CHK:    \t" + "TODO" + "\n" +
				   "---------------------------------------------";
	}
	
}

