package kimononet.stat;

import kimononet.service.Service;



/**
 * An interface that describes an object that is capable of collecting 
 * statistical information about sent and received packets. 
 * 
 * Although analyzing data is fairly similar for both simulation and production 
 * modes, gathering information can vary since in production mode data may have
 * to be aggregated via the underlying network. 
 * 
 * @author Zorayr Khalapyan
 * @since 3/12/2012
 * @version 3/12/2012
 *
 */
public interface StatMonitor extends Service {

	/**
	 * Accounts for a sent packet.
	 * @param packet The sent packet.
	 */
	public void packetSent(StatPacket packet);
	
	/**
	 * Accounts for a received packet.
	 * @param packet The received packet.
	 */
	public void packetReceived(StatPacket packet);
	
	/**
	 * Returns the gathered data.
	 * @return The gathered statistical data.
	 */
	public StatData getStats();
	
}
