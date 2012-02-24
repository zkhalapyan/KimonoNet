package kimononet.net.p2p;

import java.net.InetAddress;

/**
 * The interface represents a peer-to-peer connection. The implementation might
 * be either state-full or state-less given the requirements for the connection.  
 * 
 * @author Zorayr Khalapyan
 *
 */
public interface Connection {
	
	/**
	 * Represents the maximum number of byte allowed in a packet.
	 */
	public static final int MAX_PACKET_LENGTH = 1500;
	
	public boolean connect();
	public boolean disconnect();
	public boolean send(byte[] data, int port, InetAddress address);
	public boolean receive(byte[] data);	

}
