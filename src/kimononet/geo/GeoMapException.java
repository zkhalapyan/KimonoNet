package kimononet.geo;

public class GeoMapException extends RuntimeException {

	private static final long serialVersionUID = 6969565059347182329L;

	public GeoMapException(String message){
		super(message);
	}

	public GeoMapException(String message, Throwable cause){
		super(message, cause);
	}

}
