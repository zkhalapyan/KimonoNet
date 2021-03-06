package kimononet.geo;

import kimononet.time.SystemTimeProvider;
import kimononet.time.TimeProvider;

/**
 * GPS enabled device emulator that provided an initial location and velocity,
 * acts according to the the  
 * <a href="http://en.wikipedia.org/wiki/Random_waypoint_model">Random Waypoint 
 * Model </a>. 
 * 
 * Accordingly, each time {@link #getVelocity()} is called, the bearing of the 
 * velocity will shift according to probability specified by 
 * {@link #turnInterval} by maximum of {@link #maxTurn} either in clockwise 
 * or counterclockwise direction.
 *  
 * @author Zorayr Khalapyan
 * @since 3/14/2012
 * @version 3/14/2012
 *
 */
public class RandomWaypointGeoDevice implements GeoDevice {

	/**
	 * Current location of the traveling object.
	 */
	private GeoLocation currentLocation;
	
	/**
	 * Current velocity of the traveling object.
	 */
	private GeoVelocity velocity;
	
	/**
	 * Time provider for the current GPS device. This time provider will be used
	 * to fetch the current time of the system for calculating change in time.
	 */
	private TimeProvider timeProvider;

	/**
	 * Probability that the direction of the velocity will change each time
	 * {@link #getLocation()} is called. Probability of 1 indicates that every
	 * single time the method is invoked, the direction of the velocity will
	 * change.  
	 */
	private double turnInterval;
	
	/**
	 * In seconds.
	 */
	private static final int DEFAULT_TURN_INTERVAL = 3;
	
	/**
	 * With a probability of {@link #turnInterval}, GPS direction will change
	 * by a maximum value either clockwise or counterclockwise specified by this
	 * variable in radians. The direction may change by [-maxTurn, +maxTurn].
	 */
	private double maxTurn;
	
	/**
	 * By default, {@link #maxTurn} is set to about 45 degrees.
	 */
	private static final double DEFAULT_MAX_TURN = Math.PI / 8;

	
	private int lastTurnTime;
	
	private GeoMap map;
	
	/**
	 * Creates a new random waypoint model using {@link #DEFAULT_MAX_TURN} and
	 * {@link #DEFAULT_TURN_INTERVAL}. By default, the time provider will be 
	 * set to the {@link SystemTimeProvider}.
	 * 
	 * 
	 * @param currentLocation The starting location.
	 * 
	 * @param velocity        The starting velocity.
	 */
	public RandomWaypointGeoDevice(GeoLocation currentLocation,
								   GeoVelocity velocity){
		this(currentLocation, 
			velocity, 
			new SystemTimeProvider(), 
			DEFAULT_MAX_TURN, 
			DEFAULT_TURN_INTERVAL);
	}
	
	/**
	 * Creates a new random waypoint model using {@link #DEFAULT_MAX_TURN} and
	 * {@link #DEFAULT_TURN_INTERVAL}. By default, the time provider will be 
	 * set to the {@link SystemTimeProvider}. The third parameter also specifies
	 * the bounding map.
	 * 
	 * 
	 * @param currentLocation The starting location.
	 * 
	 * @param velocity        The starting velocity.
	 * @param map             The bounding map.
	 */
	public RandomWaypointGeoDevice(GeoLocation currentLocation, 
								   GeoVelocity velocity,
 								   GeoMap map){
		 
		this(currentLocation, velocity);
		this.map = map;
		
	}
	
	/**
	 * Creates a new GPS enabled device emulator based on the 
	 * <a href="http://en.wikipedia.org/wiki/Random_waypoint_model">Random 
	 * Waypoint Model </a>. 
	 * 
	 * @param currentLocation The starting location.
	 * 
	 * @param velocity        The starting velocity.
	 * 
	 * @param timeProvider	  Time provider for this device. Might either be 
	 * 						  system time or a specific test-oriented time 
	 * 						  provider. 
	 * 
	 * @param maxTurn         Maximum allowed turn at each hop.
	 * 
	 */
	public RandomWaypointGeoDevice(GeoLocation currentLocation, 
							   	   GeoVelocity velocity,
							   	   TimeProvider timeProvider,
							   	   double maxTurn,
							   	   double turnInterval){
		
		this.currentLocation = currentLocation;
		this.velocity        = velocity;
		this.timeProvider    = timeProvider;
		this.maxTurn         = maxTurn;
		this.turnInterval = turnInterval;
		this.lastTurnTime = timeProvider.getTime();
		
		
	}
	
	/**
	 * Retrieves the current velocity using the method {@link #getVelocity()} 
	 * and updates the current location according to elapsed time. 
	 * 
	 * @see #getVelocity()
	 */
	@Override
	public GeoLocation getLocation() {
		
		if(timeProvider.getTime() - lastTurnTime>= turnInterval){
			turn();
			lastTurnTime = timeProvider.getTime();
		}
		
		currentLocation.move(getVelocity(), timeProvider.getTime());
		
		return currentLocation;
	}


	/**
	 * With probability specified by {@link #turnInterval}, updates the 
	 * bearing direction by up to {@link #maxTurn} and returns the current
	 * velocity.
	 * 
	 * @return Current velocity.
	 */
	@Override
	public GeoVelocity getVelocity() {
		
		checkBoundaries();
		
		return velocity;
	}

	private void turn(){
		velocity.setBearing(velocity.getBearing() + (float)(-maxTurn + 2 * maxTurn * Math.random()));
	}
	
	private void checkBoundaries(){
		
		if(map == null){
			return;
		}
		
		float turn =(float)(Math.PI / 2);
		float firstQuadrant = (float)(Math.PI / 2);
		float secondQuadrant = (float)(Math.PI);
		float thirdQuadrant = (float)(3 * Math.PI / 2);
		float fourthQuadrant = (float)(2 * Math.PI);

		velocity.standardizeBearing();
		
		//Hit the top border.
		if(currentLocation.getLatitude() >= map.getUpperLeft().getLatitude()){
			
			if(0 <= velocity.getBearing() && velocity.getBearing() <= firstQuadrant){
				velocity.setBearing(velocity.getBearing() + turn);
				
			} else if(thirdQuadrant <= velocity.getBearing() && velocity.getBearing() <= fourthQuadrant){
				velocity.setBearing(velocity.getBearing() - turn);
			
			} else{
				//velocity.setBearing(secondQuadrant);
			}
			
			
		//Hit the bottom border.
		}else if(currentLocation.getLatitude() <= map.getLowerRight().getLatitude()){
			
			if(firstQuadrant <= velocity.getBearing() && velocity.getBearing() <= secondQuadrant)
				velocity.setBearing(velocity.getBearing() - turn);
			
			else if( secondQuadrant <= velocity.getBearing() && velocity.getBearing() <= thirdQuadrant){
				velocity.setBearing(velocity.getBearing() + turn);
			}
			
			
		//Hit the right border.
		}else if(currentLocation.getLongitude() >= map.getLowerRight().getLongitude()){
			
			if(0 <= velocity.getBearing() && velocity.getBearing() <= firstQuadrant){
				velocity.setBearing(velocity.getBearing() - turn);
			
			}else if(firstQuadrant <= velocity.getBearing() && velocity.getBearing() <= secondQuadrant){
				velocity.setBearing(velocity.getBearing() + turn);
			
			}
			
			
		//Hit the left border.
		}else if(currentLocation.getLongitude() <= map.getUpperLeft().getLongitude()){	
			
			// Quadrant III
			if(secondQuadrant <= velocity.getBearing() && velocity.getBearing() <= thirdQuadrant)
				velocity.setBearing(velocity.getBearing() - turn);
			
			else if(thirdQuadrant <= velocity.getBearing() && velocity.getBearing() <= fourthQuadrant)
				velocity.setBearing(velocity.getBearing() + turn);
			
		}	
		
		velocity.standardizeBearing();
	}
	
	/**
	 * Sets the current time provider.
	 * @param timeProvider New time provider to set.
	 */
	@Override
	public void setTimeProvider(TimeProvider timeProvider) {
		this.timeProvider = timeProvider;
		
	}
}
