package kimononet.peer.address;

/**
 * 
 * @author Zorayr Khalapyan
 *
 */
public class PeerAddress {

	/**
	 * Peer address length in bytes.
	 */
	public static final int ADDRESS_LENGTH = 6;
	
	/**
	 * A MAC-48 address according to IEEE 802 standard.
	 */
	private byte[] address = new byte[ADDRESS_LENGTH];
	
	/**
	 * Creates a peer address from a byte array representation of a MAC-48 
	 * address.
	 * 
	 * @param address Peer address to set.
	 */
	public PeerAddress(byte[] address){
		
		if(address.length != ADDRESS_LENGTH){
			throw new PeerAddressException("Invalid address length.");
		}
		
		this.address = address;
	}
	
	/**
	 * Creates a new peer address from a specified string.
	 * 
	 * @param address 
	 */
	public PeerAddress(String address){
		setAddress(address);
	}
	
	public void setAddress(String address){
		
		String[] hex = address.split("(\\:|\\-)");
		
		if (hex.length != this.address.length) {
			throw new PeerAddressException("Invalid address legth: " + address);
		}
		
		try {
			
			for (int i = 0; i < this.address.length; i++) {				
				this.address[i] = (byte) Integer.parseInt(hex[i], 16);
			}
		}
		catch (NumberFormatException e) {
			throw new PeerAddressException("Invalid hex digit in address: " + address, e);
		}
	}
	
	/**
	 * Returns the byte array representation of the address.
	 * @return
	 */
	public byte[] toByteArray(){
		return address;
	}
	
	/**
	 * Represents a string representation of the address.
	 */
	public String toString(){
		
		StringBuilder addressBuilder = new StringBuilder();
				
		for (int i = 0; i < address.length; i++) {
			addressBuilder.append(String.format("%02X%s", address[i], (i < address.length - 1) ? ":" : ""));
		}
		
		return addressBuilder.toString();
	}
}
