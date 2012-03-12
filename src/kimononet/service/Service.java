package kimononet.service;

/**
 * Represents a service that may be started or shutdown. Examples of services 
 * include threads that send packets, threads that receive packets, and also
 * threads that monitor statistical information.  
 * 
 * @author Zorayr Khalapyan
 * @since 3/12/2012
 * @version 3/12/2012
 *
 */
public interface Service {

	/**
	 * Starts a service.
	 */
	public void startService();
	
	/**
	 * Shuts down a service.
	 */
	public void shutdownService();
	
}
