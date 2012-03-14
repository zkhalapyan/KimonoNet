package kimononet.geo;

import java.util.Date;

import kimononet.net.parcel.Parcel;
import kimononet.net.parcel.Parcelable;

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
 * @version 3/6/2012
 *
 */
public class GeoLocation implements Parcelable {

	/**
	 * Indicates the number of bytes in a location parcel.
	 * @see #getParcelSize()
	 */
	public static final int PARCEL_SIZE = 24;
	
	/**
	 * Stores the longitude of the current GPS location.
	 * @see #getLongitude()
	 */
	private double longitude;
	
	/**
	 * Stores the latitude of the current GPS location. 
	 * @see #getLatitude()
	 */
	private double latitude;
	
	/**
	 * Stores the accuracy of the current GPS location.
	 * @see #getAccuracy()
	 */
	private float accuracy;
	
	/**
	 * Stores the UNIX time of last GPS location update in seconds.
	 * @see #getTimestamp()
	 */
	private int timestamp;

	
	/**
	 * Creates a new GPS location with the specified longitude, latitude, and
	 * accuracy. Timestamp will be set to the current System's timestamp. 
	 * 
	 * @param longitude Longitude of the current location.
	 * @param latitude Latitude of the current location.
	 * @param accuracy Accuracy (in feet) of the current geo location.
	 */
	public GeoLocation(double longitude, double latitude, float accuracy){
		setLocation(longitude, latitude, accuracy);
		this.timestamp = (int)(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * Creates a new GPS location with the specified longitude, latitude,
	 * accuracy, and timestamp.
	 * 
	 * @param longitude Longitude of the current location.
	 * @param latitude Latitude of the current location.
	 * @param accuracy Accuracy (in feet) of the current geo location.
	 * @param timestamp Timestamp when the location was fetched.
	 */
	public GeoLocation(double longitude, double latitude, float accuracy, int timestamp){
		setLocation(longitude, latitude, accuracy);
		this.timestamp = timestamp;
	}
	
	/**
	 * Creates a new location utilizing a parcel.
	 * 
	 * @param parcel Parcel constructed according to protocol specification.
	 */
	public GeoLocation(Parcel parcel){
		setLocation(parcel);
	}
	
	/**
	 * Sets GPS longitude, latitude, and accuracy. Timestamp does not change 
	 * after setting a location. 
	 * 
	 * @param longitude Longitude to set.
	 * @param latitude Latitude to set.
	 * @param accuracy Accuracy to set.
	 */
	public void setLocation(double longitude, double latitude, float accuracy){
		this.longitude = longitude;
		this.latitude = latitude;
		this.accuracy = accuracy;
	}
	
	/**
	 * Parses a location from a parcel. 
	 * 
	 * @see {@link #toByteArray()}
	 */
	public void setLocation(Parcel parcel){
		
		this.longitude = parcel.getDouble();
		this.latitude  = parcel.getDouble();
		this.accuracy  = parcel.getFloat();
		this.timestamp = parcel.getInt();	
	}
	
	/**
	 * @return A parcel representation of the current GPS location.
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
	 * Sets the current location values according to the values stored in the 
	 * specified parcel.
	 * @param parcel The parcel to parse.
	 * @see #setLocation(Parcel)
	 */
	public void parse(Parcel parcel){
		this.setLocation(parcel);
	}
	

	/**
	 * Returns the UNIX time stamp of the last location update.
	 * @return The UNIX time stamp of the last location update.
	 * @see #getTimestamp()
	 */
	public int getTimestamp(){
		return this.timestamp;
	}

	/**
	 * Sets the current timestamp.
	 * @param timestamp The timestamp for this location.
	 * @see #GeoLocation(double, double, float, int)
	 * @see #getTimestamp()
	 */
	public void setTimestamp(int timestamp){
		this.timestamp = timestamp;
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
	 * Uses Haversine formula to accurately calculate the distance to another GeoLocation.
	 * @param GeoLocation of second location to use in calculating distance.
	 * @return Returns double precision float with distance to other GeoLocation
	 */
	public double distanceTo(GeoLocation loc2)
	{
		double lo1 = this.getLongitude();
		double la1 = this.getLatitude();
		
		double lo2 = loc2.getLongitude();
		double la2 = loc2.getLatitude();
		
		double radius = 6367;
		
		double distance = Math.sin((la2-la1)/2)*Math.sin((la2-la1)/2);
		distance += Math.cos(la2-la1);
		distance += Math.cos(la1)*Math.cos(la2)*Math.sin((lo2-lo1)/2)*Math.sin((lo2-lo1)/2);
		distance = Math.asin(Math.sqrt(distance));
		distance = 2*radius*distance;
		
		return distance;
	}
	
	/**
	 * Returns the current parcel size.
	 * @return Parcel size.
	 * @see #PARCEL_SIZE
	 */
	@Override
	public int getParcelSize(){
		return PARCEL_SIZE;
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
