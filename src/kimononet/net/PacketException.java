package kimononet.net;

public class PacketException extends RuntimeException{

	/**
	 * Serial version UID for this serializable class.
	 */
	private static final long serialVersionUID = 2108877937973548684L;

	
	public PacketException(String message){
		super(message);
	}
	
	public PacketException(String message, Throwable cause){
		super(message, cause);
	}

}
