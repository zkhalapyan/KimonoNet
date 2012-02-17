package kimononet.net.p2p;

/**
 * Represents an exception associated with connections.
 * 
 * @author Zorayr Khalapyan
 *
 */
public class ConnectionException extends RuntimeException{

	/**
	 * Serial version UID for this serializable class.
	 */
	private static final long serialVersionUID = 2108877937973548684L;

	
	public ConnectionException(String message){
		super(message);
	}
	
	public ConnectionException(String message, Throwable cause){
		super(message, cause);
	}
}