package kimononet.net;

import kimononet.net.parcel.Parcel;
import kimononet.net.parcel.Parcelable;
import kimononet.peer.Peer;
import kimononet.util.ByteManipulation;

/**
 * Each packet has to a header section and a content section. Header section 
 * consists of the following fields:
 * 
 * -magic (2)
 * -version (1)
 * -type (1)
 * -timestamp (4)
 * -source peer (44)
 * 
 * The content field has variable length: 
 * -content (*)
 * 
 * @author Zorayr Khalapyan
 *
 */
public class Packet implements Parcelable{

	/**
	 * The length of the header section.
	 */
	private static final int HEADER_LENGTH = 48;
	
	/**
	 * Default magic sequence.
	 */
	private byte[] magic = new byte[] {(byte)0xBE, (byte)0xC0};
	
	/**
	 * Version of the packet. 
	 */
	private byte version;
	
	/**
	 * Packet type.
	 */
	private PacketType type;
	
	/**
	 * The UNIX timestamp of the packet. This value usually indicates when the 
	 * packet was created. 
	 */
	private int timestamp;
	
	/**
	 * Source peer of the packet - information includes peer address, location, 
	 * and velocity.
	 */
	private Peer sourcePeer;
	
	/**
	 * A variable length content of the packet.
	 */
	private byte[] contents;
	
	/**
	 * Constructs a new packet with the provided version, packet type, 
	 * and source peer.
	 * 
	 * @param version The version of the packet structure. 
	 * @param type The type of the packet.
	 * @param sourcePeer The source of the packet.
	 */
	public Packet(byte version, PacketType type, Peer sourcePeer ){
		
		this.version = version;
		this.type = type;
		this.sourcePeer = sourcePeer;
		
		this.timestamp = (int)(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * Parses a packet provided the byte representation.
	 * 
	 * @param packet A byte representation of a packet.
	 */
	public Packet(byte[] packet){
		parse(packet);
	}
	
	/**
	 * Set's current packet's contents. The new contents will be included in the
	 * parcel returned by {@link #toParcel()}.
	 * 
	 * @param contents Contents to set.
	 */
	public void setContents(byte[] contents){
		this.contents = contents;
	}
	
	/**
	 * Return's current packet's contents.
	 * @return Current packet's contents.
	 */
	public byte[] getContents(){
		return this.contents;
	}
	
	/**
	 * Parses a byte representation of a packet to an actual packet object.
	 * 
	 * @param packet Byte array representation of a packet.
	 */
	public void parse(byte[] packet){
	
		//The packet must contain the header section.
		if(packet.length < HEADER_LENGTH){
			throw new PacketException("Malformed or missing packet header.");
		}
		
		int srsPos = 0;
		
		//Extract the magic byte sequence.
		System.arraycopy(packet, srsPos, magic, 0,  magic.length);
		srsPos += magic.length;
		
		//Extract the version and packet type.
		version = packet[srsPos++];
		type = PacketType.parse(packet[srsPos++]);
		
		//Extract the timestamp.
		byte[] timestampBytes = new byte[4];
		System.arraycopy(timestampBytes, srsPos, timestampBytes, 0, 4);
		this.timestamp = ByteManipulation.toInt(timestampBytes);
		srsPos+= 8;
		
		//Now extract the contents, if any. 
		if(packet.length > HEADER_LENGTH){
			this.contents = new byte[packet.length - HEADER_LENGTH];
			System.arraycopy(packet, srsPos, contents, 0, contents.length);
		}else{
			this.contents = null;
		}
		
	}
	
	/**
	 * Returns the parcel size of the current packet. Size is calculated as 
	 * header length + length of packet contents.
	 * 
	 * @return The length of the current packet.
	 */
	@Override
	public int getParcelSize(){
		return HEADER_LENGTH + ((contents == null)? 0 : contents.length);
	}
	
	/**
	 * Returns a parcel representation of the current packet. To parse the 
	 * returned parcel back into a packet, use either the constructor
	 * {@link #Packet(byte[])} or the method {@link #parse(byte[])}.
	 *  
	 * @return A parcel representation of the current packet.
	 */
	@Override
	public Parcel toParcel(){
		
		Parcel parcel = new Parcel(getParcelSize());
		
		parcel.add(magic);
		parcel.add(version);
		parcel.add(type);
		parcel.add(timestamp);
		parcel.add(sourcePeer);
		parcel.add(contents);
		
		return parcel;
	}

}
