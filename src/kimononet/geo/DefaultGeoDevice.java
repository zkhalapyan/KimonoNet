package kimononet.geo;

/**
 * 
 * @author Zorayr Khalapyan
 *
 */
public class DefaultGeoDevice implements GeoDevice {

	@Override
	public GeoLocation getLocation() {
		
		return new GeoLocation(0, 0, 0);
	}

}
