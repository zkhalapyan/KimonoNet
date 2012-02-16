package kimononet.geo;

/**
 * Interface that represents a GPS enabled device. 
 * 
 * @author Zorayr Khalapyan
 *
 */
public interface GeoDevice {
	
	/**
	 * Returns current GPS location.
	 * @return Current GPS location.
	 */
	public GeoLocation getLocation();
}
