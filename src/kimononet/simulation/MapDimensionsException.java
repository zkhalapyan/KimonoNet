package kimononet.simulation;

public class MapDimensionsException extends RuntimeException {

	private static final long serialVersionUID = 6969565059347182329L;

	public MapDimensionsException(String message){
		super(message);
	}

	public MapDimensionsException(String message, Throwable cause){
		super(message, cause);
	}

}
