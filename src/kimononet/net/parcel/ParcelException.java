package kimononet.net.parcel;

public class ParcelException extends RuntimeException{

	/**
	 * Serial version UID for this serializable class.
	 */
	private static final long serialVersionUID = 2108877937973548684L;

	
	public ParcelException(String message){
		super(message);
	}
	
	public ParcelException(String message, Throwable cause){
		super(message, cause);
	}

}
