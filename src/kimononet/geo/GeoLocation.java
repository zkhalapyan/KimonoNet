package kimononet.geo;

/**
 * Object stores geolocation in terms of longitude, latitude, 
 * and accuracy as well as keeps track of last update time.  
 *  
 * @author Zorayr Khalapyan
 * @since 2/9/2012
 *
 */
public class GeoLocation {

	/**
	 * Stores the longitude of the current GPS location.
	 */
	private double longitude;
	
	/**
	 * Stores the latitude of the current GPS location. 
	 */
	private double latitude;
	
	/**
	 * Stores the accuracy of the current GPS location.
	 */
	private double accuracy;
	
	/**
	 * Stores the UNIX time of last GPS location update in seconds.
	 */
	private long timestamp;
	
	public GeoLocation(double longitude, double latitude, double accuracy){
		setLocation(longitude, latitude, accuracy);
	}
	
	public void setLocation(double longitude, double latitude, double accuracy){
		this.longitude = longitude;
		this.latitude = latitude;
		this.accuracy = accuracy;
		
		updateTimestamp();
	}
	
	/**
	 * Resets the timestamp to the current system's time. 
	 */
	private void updateTimestamp(){
		this.timestamp = System.currentTimeMillis() / 1000;
	}
	
	/**
	 * Returns the UNIX timestamp of the last location update.
	 * @return The UNIX timestamp of the last location update.
	 */
	public long getLastUpdateTime(){
		return this.timestamp;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getAccuracy() {
		return accuracy;
	}

	/**
	 * Converts this GPS location to a byte array representation. 
	 * @return A byte array representation of the current GPS location.
	 */
	public byte[] toByteArray(){
		
		//byte[] lon = longitude
		return null;
	}
	
	/**
	 * Returns a string representation of the current location. The string will
	 * contain the longitude, latitude, and accuracy separated by a tab
	 * character. 
	 * 
	 * @return A string representation of the current location.
	 */
	@Override
	public String toString(){
		return "Longitude: " + getLongitude() + "\t" + 
		       "Latitude: " + getLatitude() + "\t" +
		       "Accuracy: " + getAccuracy();
	}
}
