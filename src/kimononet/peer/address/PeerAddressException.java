package kimononet.peer.address;

/**
 * Represents an exception associated with parsing or handling peer addresses.
 * 
 * @author Zorayr Khalapyan
 *
 */
public class PeerAddressException extends RuntimeException{

	/**
	 * Serial version UID for this serializable class.
	 */
	private static final long serialVersionUID = 2108877937973548684L;

	public PeerAddressException(String message){
		super(message);
	}
	
	public PeerAddressException(String message, Throwable cause){
		super(message, cause);
	}
}
