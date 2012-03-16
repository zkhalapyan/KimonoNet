package kimononet.geo;

import kimononet.time.SystemTimeProvider;
import kimononet.time.TimeProvider;

/**
 * 
 * @author Zorayr Khalapyan
 * @version 3/6/2012
 *
 */
public class DefaultGeoDevice implements GeoDevice {
	
	/**
	 * 
	 * @see #getLocation()
	 */
	private GeoLocation currentLocation;
	
	/**
	 * @see #getVelocity()
	 */
	private GeoVelocity currentVelocity;
	
	/**
	 * Create a GPS device emulator that will return a location initialized to
	 * (0,0) and a velocity with speed and velocity set to 0.
	 */
	public DefaultGeoDevice(){
		this(new GeoLocation(0.0, 0.0, 0.0f), new GeoVelocity(0, 0));
	}
	/**
	 * Creates a new device that will fetch the System's time instead of 
	 * utilizing a time provider.
	 */
	public DefaultGeoDevice(GeoLocation location, GeoVelocity velocity){

		this.currentLocation = location;
		this.currentVelocity = velocity;
	}
	
	
	/**
	 * Returns the current location.
	 * @return Current location.
	 */
	@Override
	public GeoLocation getLocation() {
		return currentLocation;
	}

	/**
	 * Returns the current velocity.
	 */
	@Override
	public GeoVelocity getVelocity() {
		return this.currentVelocity;
	}
	
	/**
	 * Since time is not going to be used by this class, this method is ignored. 
	 * @param timeProvider The time provider to set.
	 */
	public void setTimeProvider(TimeProvider timeProvider){
		//Discard the set time provider, since there is no need to fetch the 
		//current time.
	}
}
