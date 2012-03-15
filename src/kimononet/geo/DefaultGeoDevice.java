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
	 * If specified, the device will utilize this time provider  in order to 
	 * fetch the current peer's time.
	 */
	private TimeProvider timeProvider;
	
	/**
	 * Creates a new device that will fetch the System's time instead of 
	 * utilizing a time provider.
	 */
	public DefaultGeoDevice(){
		this.timeProvider = new SystemTimeProvider();
	}
	
	/**
	 * Creates a new default geo device with the specified time provider.
	 * 
	 * @param timeProvider The time provider that will be used to fetch the 
	 * 	                   System's current time.
	 */
	public DefaultGeoDevice(TimeProvider timeProvider){
		this.timeProvider = timeProvider;
	}
	
	/**
	 * Returns the current geo location.
	 * @return Current geo location.
	 */
	@Override
	public GeoLocation getLocation() {
		
		return new GeoLocation(0, 0, 0, timeProvider.getTime());
	}
	
	

	/**
	 * Sets the time provider for the 
	 * @param timeProvider The time provider to set.
	 */
	public void setTimeProvider(TimeProvider timeProvider){
		this.timeProvider = timeProvider;
	}

	@Override
	public GeoVelocity getVelocity() {
		return new GeoVelocity(0, 0); 
	}
}
