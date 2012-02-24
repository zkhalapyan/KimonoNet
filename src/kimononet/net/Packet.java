package kimononet.net;

import kimononet.util.ByteManipulation;

/**
 * Each packet has to a header section and a content section. Header section 
 * consists of the following fields:
 * 
 * -magic (2)
 * -version (1)
 * -type (1)
 * -time stamp (8)
 * -content (*)
 * 
 * @author Zorayr Khalapyan
 *
 */
public class Packet {

	/**
	 * The length of the header section.
	 */
	private static final int HEADER_LENGTH = 12;
	
	/**
	 * The number of bytes representing the magic bytes sequence. 
	 */
	private static final int MAGIC_BYTES_LENGTH = 2;
	
	/**
	 * By default, magic bytes are initialized to all 0s.
	 */
	private byte[] magic = new byte[] {0x00, 0x00};
	
	/**
	 * Version of the packet. 
	 */
	private byte version;
	
	/**
	 * Packet type.
	 */
	private PacketType type;
	
	/**
	 * Time stamp of the packet.
	 */
	private long timestamp;
	
	/**
	 * A variable length content of the packet.
	 */
	private byte[] contents;
	
	public Packet(byte[] packet){
		parse(packet);
	}
	
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
		byte[] timestampBytes = new byte[8];
		System.arraycopy(timestampBytes, srsPos, timestampBytes, 0, 8);
		this.timestamp = ByteManipulation.toLong(timestampBytes);
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
	 * Returns the length of the current packet.
	 * @return The length of the current packet.
	 */
	public int getPacketLength(){
		return HEADER_LENGTH + ((contents == null)? 0 : contents.length);
	}
	
	/**
	 * Returns a byte array representation of the current packet. To parse the 
	 * returned byte array back into a packet, use either the constructor
	 * {@link #Packet(byte[])} or the method {@link #parse(byte[])}.
	 *  
	 * @return A byte array representation of the current packet.
	 */
	public byte[] getByteArray(){
		
		byte[] packet = new byte[HEADER_LENGTH + contents.length];
		
		int dstPos = 0;
		
		//Copy the magic byte sequence.
		System.arraycopy(magic, 0, packet, dstPos, magic.length);
		dstPos += magic.length;
		
		//Copy the version byte and packet type.
		packet[dstPos++] = version;
		packet[dstPos++] = type.getFlag();
		
		//Copy the time stamp.
		byte[] timestampBytes = ByteManipulation.toByteArray(timestamp);
		System.arraycopy(timestampBytes, 0, packet, dstPos, timestampBytes.length);
		dstPos+= timestampBytes.length;
		
		//Now extract the contents, if any. 
		if(packet.length > HEADER_LENGTH){
			this.contents = new byte[packet.length - HEADER_LENGTH];
			System.arraycopy(packet, srsPos, contents, 0, contents.length);
		}else{
			this.contents = null;
		}
		
		
		
		
		
		return packet;
	}
	
	
	
	
	
}
