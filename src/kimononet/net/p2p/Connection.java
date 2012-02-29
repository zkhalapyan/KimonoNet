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
	
	public static final String BROADCAST_ADDRESS = "255.255.255.255";
	
	/**
	 * Default timeout for receiving packets.
	 */
	public static final int DEFAULT_TIMEOUT = 200;
	
	
	/**
	 *  If the IP address is 0.0.0.0, the socket will be bound to the wildcard
	 *  address, an IP address chosen by the kernel.
	 */
	public static final String WILDCARD_ADDRESS = "0.0.0.0";
	
	public boolean setTimeout(int timeout);
	public boolean isConnected();
	public boolean connect();
	public boolean setBlocking(boolean blocking);
	public boolean disconnect();
	public boolean send(byte[] data, int port, InetAddress address);
	public byte[] receive();	

}
