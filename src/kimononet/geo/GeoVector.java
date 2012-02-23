package kimononet.geo;

import java.util.Date;

/**
 * The class represents a GPS velocity vector. 
 * 
 * <pre>
 * 
 * GeoLocation initialLocation = new GeoLocation(0.0, 0.0, 0.0);
 * GeoVector v = new GeoVector(initialLocation);
 * 
 * GeoLocation finalLocation = new GeoLocation(1.0, 1.0, 0.0);
 * 
 * v.updateLocation(finalLocation);
 * 
 * System.out.println(v);
 * 
 * </pre> 
 * 
 * @author Zorayr Khalapyan
 *
 */
public class GeoVector {

	
	/**
	 * Change in longitude over time.
	 */
	private double dLongitude = 0;
	
	/**
	 * Change in latitude over time.
	 */
	private double dLatitude = 0;
	
	/**
	 * Average accuracy of all considered locations.
	 */
	private double averageAccuracy = 0;
	
	/**
	 * Last reported location.
	 */
	private GeoLocation currentLocation;
	
	/**
	 * Creates a new vector initialized at the provided location. 
	 * 
	 * Initially, both change in longitude and change in latitude will be 
	 * initialized to zero and will be modified once current location is updated 
	 * using {@link #updateLocation(GeoLocation)}.
	 *  
	 * @param currentLocation The current location of the vector. 
	 */
	public GeoVector(GeoLocation currentLocation){
		this.currentLocation = currentLocation;
	}
	
	public void updateLocation(GeoLocation newLocation){
		
		double dTime = currentLocation.getLastUpdateTime() - newLocation.getLastUpdateTime();
		
		this.dLongitude = (currentLocation.getLongitude() - currentLocation.getLongitude()) / dTime;
		this.dLatitude = (currentLocation.getLatitude() - currentLocation.getLatitude()) / dTime;
			
		this.averageAccuracy = (averageAccuracy + newLocation.getAccuracy()) / 2;
	    
		this.currentLocation = newLocation;
	}
	
	public double getLongitudeComponent(){
		return this.dLongitude;
	}
	
	public double getLatitudeComponent(){
		return this.dLatitude;
	}

	public double getAverageAccuracy(){
		return this.averageAccuracy;
	}
	
	/**
	 * Returns a string representation of the current vector.
	 * @return A string representation of the current vector.
	 */
	public String toString(){
		
		return "dLongitude: "       + getLongitudeComponent() + "\t" + 
	       	   "dLatitude: "        + getLatitudeComponent() + "\t" +
	       	   "Average Accuracy: " + getAverageAccuracy() + "\t" +
	       	   "Timestamp: "        + new Date(currentLocation.getLastUpdateTime() * 1000);
	}
}
	