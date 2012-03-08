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
 * A subclass of Packet that represents a data packet.
 * 
 * Each data packet has a common header section from the main packet type,
 * a general data header, an extended data header for when in perimeter mode,
 * and a content section. Data packet consists of the following fields:
 * 
 * COMMON HEADER
 * HDR-MAGIC (2)
 * HDR-VERSION (1)
 * HDR-TYPE (1)
 * HDR-SRC-ID (8)
 * HDR-SRC-LOC (24)
 * HDR-SRC-VEC (8)
 * 
 * Total Length: 44.
 * 
 * DATA HEADER
 * HDR-DST-ID (8)
 * HDR-DST-LOC (24)
 * HDR-FWD-DST-ID (8)
 * HDR-FWD-MODE (1)
 * HDR-DATA-LEN (2) 
 * HDR-QOS (1)
 * HDR-HDR-CHK (4)
 * 
 * Total Length: 48.
 * 
 * EXTENDED DATA HEADER
 * XHDR-ENTERED-LOC (24)
 * XHDR-FACE-ENTERED-LOC (24)
 * XHDR-FACE-FIRST-EDGE-SRC (24)
 * XHDR-FACE-FIRST-EDGE-DST (24)
 * 
 * Total Length: 96.
 * 
 * The content field has variable length: 
 * -content (*)
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
	private static final short DATA_HDR_SIZE = PeerAddress.PARCEL_SIZE
											 + GeoLocation.PARCEL_SIZE
											 + PeerAddress.PARCEL_SIZE
											 + 1 + 2 + 1 + 4;
	
	/**
	 * Size of Extended Data Packet Header in bytes.
	 */
	private static final short DATA_XHDR_SIZE = 4 * GeoLocation.PARCEL_SIZE;
	
	
	/**
	 * Holds the peer that this packet is being sent to.
	 */
	private Peer hdr_dst;
	
	/**
	 * Holds address of the next hop for this data packet.
	 */
	private PeerAddress hdr_fwd_dst_id;

	/**
	 * Flag that indicates the forwarding mode, can be GREEDY or PERIMETER.
	 */
	private byte hdr_fwd_mode;
	
	/**
	 * Short that contains the length of the data payload for this data packet.
	 */
	private short hdr_data_len;

	/**
	 * Flag that indicates the quality of service, can be CONTROL, COMMUNICATION, or REGULAR.
	 */
	private byte hdr_qos;
	
	/**
	 * If the data packet is in perimeter then this extended header field contains
	 * the location that it entered into perimeter forwarding.
	 */
	private PeerAddress xhdr_entered_loc;
	
	/**
	 * TODO: Figure out what this variable is.
	 */
	private PeerAddress xhdr_face_entered_loc;
	
	/**
	 * TODO: Figure out what this variable is.
	 */
	private PeerAddress xhdr_face_first_edge_src;
	
	/**
	 * TODO: Figure out what this variable is.
	 */
	private PeerAddress xhdr_face_first_edge_dst;
	
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
		
		this.hdr_dst = peer;
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
	
	/**
	 * Parses parcel form of data packet into data packet structure
	 * with fields set to their appropriate data in the parcel.
	 * 
	 * @param parcel Parcel representation of a data packet.
	 */
	public void parse(Parcel parcel){
		
		super.parse(parcel);
		
		Parcel contents = getContents();
			
		if(contents == null){
			throw new PacketException("Malformed data packet. Missing contents.");
		}
		
		// HDR-DST-ID (8)
		this.hdr_dst = new Peer(new PeerAddress(parcel));
		
		// HDR-DST-LOC (24)
		this.hdr_dst.setLocation(new GeoLocation(parcel));
		
		// HDR-FWD-DST-ID (8)
		this.hdr_fwd_dst_id = new PeerAddress(parcel);
		
		// HDR-FWD-MODE (1)
		this.hdr_fwd_mode = parcel.getByte();
		
		// HDR-DATA-LEN (2)
		this.hdr_data_len = parcel.getShort();
		
		// HDR-QOS (1)
		this.hdr_qos = parcel.getByte();
		
		// HDR-HDR-CHK (4)
		int checksum = parcel.getInt();
		
		// XHDR (96)
		if(this.hdr_fwd_mode == ForwardMode.PERIMETER.getFlag())
		{
			this.xhdr_entered_loc = new PeerAddress(parcel);
			this.xhdr_face_entered_loc = new PeerAddress(parcel);
			this.xhdr_face_first_edge_src = new PeerAddress(parcel);
			this.xhdr_face_first_edge_dst = new PeerAddress(parcel);
		}
		else
		{
			for(int i=0; i<DataPacket.DATA_XHDR_SIZE; i++)
				parcel.getByte();
		}
		
		// Extract payload
		byte[] data = new byte[this.hdr_data_len];
		parcel.getByteArray(data);
		this.payload = data;
		
		//ToDo: Magic Check?
		
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
		short dataParcelLength = (short) (DATA_HDR_SIZE + DATA_XHDR_SIZE + payloadLength);
		
		Parcel dataParcel = new Parcel(dataParcelLength);
		
		// HDR-DST-ID (8)
		dataParcel.add(this.hdr_dst.getAddress());
		
		// HDR-DST-LOC (24)
		dataParcel.add(this.hdr_dst.getLocation());
		
		// HDR-FWD-DST-ID (8)
		// Next Hop ID initially set to 0
		dataParcel.add(((long)0x0));
		
		// HDR-FWD-MODE (1)
		dataParcel.add(ForwardMode.GREEDY.getFlag());
		
		// HDR-DATA-LEN (2)
		dataParcel.add(payloadLength);
		
		// HDR-QOS (1)
		// Quality of Service set to MEDIUM
		dataParcel.add(QualityOfService.REGULAR.getFlag());
		
		// HDR-HDR-CHK (4)
		// Checksum initially set to 0
		dataParcel.add(((int)0x0));
		
		// XHDR (96)
		// Set Extended Header initially to 0
		for(int i=0; i<DATA_XHDR_SIZE; i++) {
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
		return hdr_dst;
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
				   "HDR-DST-ID:  \t" + this.hdr_dst.getAddress() + "\n" + 
				   "HDR-DST-LOC: \t" + this.hdr_dst.getLocation() + "\n" +
				   "HDR-FWD-DST-ID: \t" + "TODO" + "\n" + 
				   "HDR-FWD-MODE:   \t" + "TODO" + "\n" + 
				   "HDR-DATA-LEN: \t" + this.payload.length + "\n" + 
				   "HDR-QOS:    \t" + "TODO" + "\n" +  
				   "HDR-HDR-CHK:    \t" + "TODO" + "\n" +
				   "---------------------------------------------";
	}
	
}

