package kimononet.geo;

public class GeoLocationException extends RuntimeException {

	private static final long serialVersionUID = -1721644137331118596L;

	public GeoLocationException(String message){
		super(message);
	}

	public GeoLocationException(String message, Throwable cause){
		super(message, cause);
	}

}
