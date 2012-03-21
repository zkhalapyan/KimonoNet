package kimononet.geo;
import kimononet.net.parcel.Parcel;
import kimononet.net.parcel.Parcelable;

/**
 * The class represents a GPS velocity vector in terms of speed and a bearing.
 * 
 * @author Zorayr Khalapyan
 * @version 3/14/2012
 *
 */
public class GeoVelocity implements Parcelable {
	
	/**
	 * Indicates the number of bytes in a velocity parcel.
	 */
	public static final int PARCEL_SIZE = 8;
	
	/**
	 * Change in location over time in m/s.
	 */
	private float speed;

	/**
	 * Bearing in radians. 
	 */
	private float bearing;
	
	/**
	 * Creates a new velocity object with the specified speed and bearing. 
	 * @param speed Speed in  m/s.
	 * @param bearing Bearing in radians.
	 */
	public GeoVelocity(float speed, float bearing){
		this.speed = speed;
		this.bearing = bearing;
	}
	
	/**
	 * Creates a new 
	 * @param parcel
	 */
	public GeoVelocity(Parcel parcel){
		parse(parcel);
	}	
	
	/**
	 * Returns the current speed in m/s.
	 * @return Current speed in m/s
	 */
	public float getSpeed(){
		return this.speed;
	}
	
	/**
	 * Returns bearing in radians.
	 * @return Bearing in radians.
	 */
	public float getBearing(){
		return this.bearing;
	}
	
	/**
	 * Set a new bearing value in radians.
	 * @param bearing New bearing to set in radians.
	 */
	public void setBearing(float bearing){
		this.bearing = bearing;
	}
	
	/**
	 * Parses a speed and bearing values from the provided parcel.
	 * @param parcel Parcel representing a velocity.
	 */
	public void parse(Parcel parcel){
		speed   = parcel.getFloat();
		bearing = parcel.getFloat();
	}
	
	/**
	 * Returns a parcel representation of the current velocity.
	 */
	@Override
	public Parcel toParcel(){
		
		Parcel parcel = new Parcel(getParcelSize());
		
		parcel.add(this.getSpeed());
		parcel.add(this.getBearing());
		
		return parcel;
	}
	
	/**
	 * Returns a string representation of the current vector.
	 * @return A string representation of the current vector.
	 */
	public String toString(){
						  
 		return "Speed: "   + getSpeed()   + "\n" +
			   "Bearing: " + getBearing() + "\n";
	}
	
	/**
	 * Returns the size of the parcel that represents the current velocity.
	 */
	@Override
	public int getParcelSize(){
		return PARCEL_SIZE;
	}

	/**
	 * Adjust the current bearing to be within the [0, 2Pi) range. 
	 */
	public void standardizeBearing(){
		
		//Make sure that the current bearing is positive.
		while(getBearing() < 0){
			setBearing((float)(getBearing() + Math.PI * 2));
		}
		
		//Clamp bearings larger than two PI.
		setBearing((float)(getBearing() % (2 * Math.PI)));
		
	}
}
	