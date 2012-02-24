package kimononet.net;

import kimononet.net.parcel.*;

/**
 * Enumeration object represents various types of packets and associated byte
 * flags. The enumeration provides the functionality to convert from an 
 * enumerated member type to a byte representation and from byte representation 
 * to an enumerated type. Please see {@link #getFlag()} and {@link #parse(byte)}.
 * 
 * @author Zorayr Khalapyan
 *
 */
public enum PacketType implements Parcelable{
	
	BEACON((byte)0x01),
	
	BEACON_ACK((byte)0x02),
	
	DATA((byte)0x03);
	
	/**
	 * A unique byte representation of the enumeration.
	 */
	private byte flag;
	
	/**
	 * Constructs an enumerated member with an associated byte representation.
	 * @param flag The byte representation of the packet type.
	 */
	private PacketType(byte flag){
		this.flag = flag;
	}
	
	/**
	 * Returns a byte representation of the enumerated packet type. This value
	 * should be used as a type representation within byte packets.
	 *  
	 * @return A byte representation of the enumerated packet type. 
	 */
	public byte getFlag(){
		return this.flag;
	}
	
	/**
	 * Returns the number of bytes used to represent a packet type.
	 * @return the number of bytes used to represent a packet type.
	 */
	public int getTypeLength(){
		return 1;
	}
	
	/**
	 * Returns a parcel representation of the packet type.
	 * @return Parcel representation of the packet type.
	 */
	public Parcel toParcel(){
		return new Parcel(this.getFlag());
	}
	
	/**
	 * The method converts a provided byte flag into an appropriate packet type.
	 * In case the provided flag cannot be matched with a packet type, null 
	 * value will be returned.
	 * 
	 * @param flag The byte representation of a packet type.
	 * @return A packet type enumerated member matched to the provided flag.
	 */
	public static PacketType parse(byte flag){
		
		for(PacketType type : PacketType.values())
			if(type.getFlag() == flag)
				return type;
		
		return null;
	}
}
