package kimononet.geo;


/**
 * 
 * Represents a map bounded by upper left and bottom right corners.
 * 
 * @author James Hung
 * @author Zorayr Khalapyan
 *
 */
public class GeoMap {

	/**
	 * Represented the boundary at the upper left corner.
	 */
	private GeoLocation upperLeft;
	
	/**
	 * Represents the boundary at the bottom right corner. 
	 */
	private GeoLocation lowerRight;
	
	/**
	 * Provided a width and a height of a certain map, the constructor creates 
	 * a upper left corner station at (0, 0) and lower right corner according
	 * to the dimension.
	 * 
	 * @param width  Width of the map in meters.
	 * @param height Height of the map in meters. 
	 */
	public GeoMap(int width, int height){
		
		this.upperLeft = new GeoLocation(0, 0, 0);
		this.lowerRight = new GeoLocation(Math.toDegrees(height / (double)GeoLocation.EARTH_MEDIAN_RADIUS),
										  Math.toDegrees(width / (double)GeoLocation.EARTH_MEDIAN_RADIUS), 0);
	}

	public GeoMap(GeoLocation upperLeft, GeoLocation lowerRight) {
		setDimensions(upperLeft, lowerRight);
	}

	public GeoLocation getUpperLeft() {
		return upperLeft;
	}

	public GeoLocation getLowerRight() {
		return lowerRight;
	}

	public void setDimensions(GeoLocation ul, GeoLocation lr) {
		
		if (ul.getLongitude() >= lr.getLongitude())
			throw new GeoMapException("Left longitude must be less than right longitude.");
		
		else if (ul.getLatitude() <= lr.getLatitude())
			throw new GeoMapException("Upper latitude must be greater than lower latitude.");
		else {
			upperLeft = ul;
			lowerRight = lr;
		}
	}
	
	public String toString(){
		return "UpperLeft: (" + (float)upperLeft.getLongitude() + ", " + (float)upperLeft.getLatitude() + ") " +
			   "LowerRight: (" + (float)lowerRight.getLongitude() + ", " + (float)lowerRight.getLatitude() + ")";
	}

}
