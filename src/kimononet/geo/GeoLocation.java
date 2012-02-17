package kimononet.geo;

import java.util.Date;

import kimononet.util.ByteManipulation;

/**
 * Object stores geolocation in terms of longitude, latitude, and accuracy as 
 * well as keeps track of last update time. Please see below for an example:
 * 
 * <pre>
 * GeoLocation location1 = new GeoLocation(1.0, 2.0, 3.0);		
 * GeoLocation location2 = new GeoLocation(4.0, 5.0, 6.0);
 *
 * System.out.println(location2);
 * 
 * //Convert location1 to a byte array representation. 
 * byte[] location1Bytes = location1.toByteArray()
 * 
 * //Set location from a byte array.
 * location2.setLocation(location1Bytes);
 * 
 * System.out.println(location2);
 * </pre>
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
	
	/**
	 * Creates a new GPS location with the specified longitude, latitude, and
	 * accuracy. Time stamp will be set to the current System's timestamp. 
	 * 
	 * @param longitude Longitude to set.
	 * @param latitude Latitude to set.
	 * @param accuracy Accuracy to set.
	 */
	public GeoLocation(double longitude, double latitude, double accuracy){
		setLocation(longitude, latitude, accuracy);
	}
	
	public GeoLocation(byte[] location){
		setLocation(location);
	}
	
	/**
	 * Sets GPS longitude, latitude, and accuracy, and also updates the current
	 * time stamp. 
	 * 
	 * @param longitude Longitude to set.
	 * @param latitude Latitude to set.
	 * @param accuracy Accuracy to set.
	 */
	public void setLocation(double longitude, double latitude, double accuracy){
		this.longitude = longitude;
		this.latitude = latitude;
		this.accuracy = accuracy;
		
		updateTimestamp();
	}
	
	/**
	 * Restores a GPS location from a byte array representation. The byte array
	 * must have the following format:  
	 *  
	 * [0-8)  : UNIX time stamp representing last GPS location update (long).
	 * [8-16) : GPS longitude (double).
	 * [16-24): GPS latitude (double).
	 * [24-32): GPS accuracy (double).
	 * 
	 * @param location A byte array representing a GPS location. 
	 * 
	 * @see kimononet.geo.GeoLocation#toByteArray()
	 */
	public void setLocation(byte[] location){
		
		//Create byte arrays to store the extracted byte arrays.
		byte[] tim = new byte[8];
		byte[] lon = new byte[8];
		byte[] lat = new byte[8];
		byte[] acc = new byte[8];
		
		//Extract information from the location byte array.
		System.arraycopy(location, 0 * 8, tim, 0, 8);
		System.arraycopy(location, 1 * 8, lon, 0, 8);
		System.arraycopy(location, 2 * 8, lat, 0, 8);
		System.arraycopy(location, 3 * 8, acc, 0, 8);
		
		//Convert byte array to actual values.
		this.timestamp = ByteManipulation.toLong(tim);
		this.longitude = ByteManipulation.toDouble(lon);
		this.latitude = ByteManipulation.toDouble(lat);
		this.accuracy = ByteManipulation.toDouble(acc);
	}
	
	/**
	 * Resets the time stamp to the current system's time. 
	 */
	private void updateTimestamp(){
		this.timestamp = System.currentTimeMillis() / 1000;
	}
	
	/**
	 * Returns the UNIX time stamp of the last location update.
	 * @return The UNIX time stamp of the last location update.
	 */
	public long getLastUpdateTime(){
		return this.timestamp;
	}

	/**
	 * Returns current GPS longitude.
	 * @return Current GPS longitude.
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Returns current GPS latitude.
	 * @return Current GPS latitude.
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Returns current GPS accuracy.
	 * @return Current GPS accuracy.
	 */
	public double getAccuracy() {
		return accuracy;
	}

	/**
	 * Converts this GPS location to a byte array representation. The final byte
	 * array will have the following structure:
	 * 
	 * [0-8)  : UNIX time stamp representing last GPS location update (long).
	 * [8-16) : GPS longitude (double).
	 * [16-24): GPS latitude (double).
	 * [24-32): GPS accuracy (double).
	 *   
	 * @return A byte array representation of the current GPS location.
	 * 
	 * @see #setLocation(byte[])
	 */
	public byte[] toByteArray(){
		
		//Convert each component of a GPS location into a byte array.
		byte[][] packaged = new byte[4][];
		packaged[0] = ByteManipulation.toByteArray(this.getLastUpdateTime());
		packaged[1] = ByteManipulation.toByteArray(this.getLongitude());
		packaged[2] = ByteManipulation.toByteArray(this.getLatitude());
		packaged[3] = ByteManipulation.toByteArray(this.getAccuracy());
		
		//Create an array to store all the bytes.
		byte[] location = new byte[8 * 4];
		
		for(int i = 0; i < packaged.length; i++){
			System.arraycopy(packaged[i], 0, location, i * 8, packaged[i].length);	
		}
		
		return location;
	}
	
	/**
	 * Returns a string representation of the current location. The string will
	 * contain the longitude, latitude, accuracy, and time stamp separated by 
	 * tab characters. 
	 * 
	 * @return A string representation of the current location.
	 */
	@Override
	public String toString(){
		
		return "Longitude: " + getLongitude() + "\t" + 
		       "Latitude: " + getLatitude() + "\t" +
		       "Accuracy: " + getAccuracy() + "\t" +
		       "Timestamp: " + new Date(timestamp * 1000);
	}
}
