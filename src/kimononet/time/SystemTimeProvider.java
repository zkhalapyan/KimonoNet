package kimononet.time;

/**
 * Time provider that utilized the System's clock in order to fetch the current
 * timestamp.
 * 
 * @author Zorayr Khalapyan
 * @since 3/6/2012
 * @version 3/6/2012
 *
 */
public class SystemTimeProvider implements TimeProvider {

	/**
	 * Returns System's UTC timestamp. 
	 * @return System's current UTC timestamp. 
	 */
	@Override
	public int getTime() {
		return (int)(System.currentTimeMillis() / 1000.0);
	}

}
