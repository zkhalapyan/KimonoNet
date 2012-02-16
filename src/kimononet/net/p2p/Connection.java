package kimononet.net.p2p;

/**
 * The interface represents a peer-to-peer connection. The implementation might
 * be either state-full or state-less given the requirements for the connection.  
 * 
 * @author Zorayr Khalapyan
 *
 */
public interface Connection {
	
	/**
	 * Timeout for waiting to accept connections from clients.
	 */
	public static int SERVER_ACCEPT_TIMEOUT = 100;
	
	/**
	 * Buffer size in bytes of the received UDP packets.
	 */
	public static int PACKET_BUFFER_SIZE = 2048;
	
	public boolean connect();
	public boolean disconnect();
	public boolean send(byte[] data);
	public boolean receive();	

}
