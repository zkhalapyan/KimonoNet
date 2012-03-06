package kimononet.time;

/**
 * Time provider that should be used in simulation mode. Timestamp returned by
 * the provider is the time specified during construction.
 * 
 * @author Zorayr Khalapyan
 * @since 3/6/2012
 * @version 3/6/2012
 *
 */
public class SimulationTimeProvider implements TimeProvider {

	/**
	 * Timestamp saved during construction and returned by 
	 * {@link #getUTCTime()}.
	 */
	private int currentTime;
	
	/**
	 * Create a time provider with the specified timestamp.
	 * @param currentTime
	 */
	public SimulationTimeProvider(int currentTime){
		this.currentTime = currentTime;
	}
	
	/**
	 * Returns the specified UTC timestamp.
	 * @return Specified UTC timestamp.
	 */
	@Override
	public int getTime() {
		return this.currentTime;
	}
	
	/**
	 * Sets the current time. This value will be returned by 
	 * {@link #getUTCTime()} next time it is invoked.
	 * 
	 * @param currentTime Current time to set.
	 */
	public void setCurrentTime(int currentTime){
		this.currentTime = currentTime;
	}

}
