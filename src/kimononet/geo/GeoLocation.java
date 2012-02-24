package kimononet.geo;

import java.util.Date;

import kimononet.net.parcel.Parcel;
import kimononet.net.parcel.Parcelable;
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
public class GeoLocation implements Parcelable {

	/**
	 * Indicates the number of bytes in a location parcel.
	 */
	public static final int PARCEL_SIZE = 24;
	
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
	private float accuracy;
	
	/**
	 * Stores the UNIX time of last GPS location update in seconds.
	 */
	private int timestamp;
	
	/**
	 * Creates a new GPS location with the specified longitude, latitude, and
	 * accuracy. Time stamp will be set to the current System's timestamp. 
	 * 
	 * @param longitude Longitude to set.
	 * @param latitude Latitude to set.
	 * @param accuracy Accuracy to set.
	 */
	public GeoLocation(double longitude, double latitude, float accuracy){
		setLocation(longitude, latitude, accuracy);
	}
	
	public GeoLocation(byte[] location){
		setLocation(location);
	}
	
	/**
	 * Sets GPS longitude, latitude, and accuracy, and also updates the current
	 * timestamp. 
	 * 
	 * @param longitude Longitude to set.
	 * @param latitude Latitude to set.
	 * @param accuracy Accuracy to set.
	 */
	public void setLocation(double longitude, double latitude, float accuracy){
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
	 * [24-28): GPS accuracy (float).
	 * 
	 * @param location A byte array representing a GPS location. 
	 * 
	 * @see {@link #toByteArray()}
	 */
	public void setLocation(byte[] location){
		
		//Create byte arrays to store the extracted byte arrays.
		byte[] tim = new byte[ByteManipulation.getLength(this.timestamp)];
		byte[] lon = new byte[ByteManipulation.getLength(this.longitude)];
		byte[] lat = new byte[ByteManipulation.getLength(this.latitude)];
		byte[] acc = new byte[ByteManipulation.getLength(this.accuracy)];
		
		//Offset position with the provided location byte array. 
		int srcPos = 0;
		
		//Extract information from the location byte array.
		System.arraycopy(location, srcPos, lon, 0, lon.length);
		srcPos += lon.length;
		
		System.arraycopy(location, srcPos, lat, 0, lat.length);
		srcPos += lat.length;
		
		System.arraycopy(location, srcPos, acc, 0, acc.length);
		srcPos += acc.length;
		
		System.arraycopy(location, srcPos, tim, 0, tim.length);
		srcPos += tim.length;
		
		//Convert byte array to actual values.
		this.timestamp = ByteManipulation.toInt(tim);
		this.longitude = ByteManipulation.toDouble(lon);
		this.latitude = ByteManipulation.toDouble(lat);
		this.accuracy = ByteManipulation.toFloat(acc);
		
	}
	
	/**
	 * Converts this GPS location to a parcel. The final parcel will have the 
	 * following structure:
	 * 
	 * -GPS longitude (double/8).
	 * -GPS latitude (double/8).
	 * -GPS accuracy (float/4).
	 * -UNIX time stamp (int/4)
	 *   
	 * @return A byte array representation of the current GPS location.
	 */
	public Parcel toParcel(){
		
		Parcel parcel = new Parcel(24);
		
		parcel.add(this.getLongitude());
		parcel.add(this.getLatitude());
		parcel.add(this.getAccuracy());
		parcel.add(this.timestamp);
		
		return parcel;
	}	
	
	/**
	 * Resets the time stamp to the current system's time. 
	 */
	private void updateTimestamp(){
		this.timestamp = (int)(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * Returns the UNIX time stamp of the last location update.
	 * @return The UNIX time stamp of the last location update.
	 */
	public int getLastUpdateTime(){
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
	public float getAccuracy() {
		return accuracy;
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
