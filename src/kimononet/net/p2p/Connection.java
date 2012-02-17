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
	
	public boolean connect();
	public boolean disconnect();
	public boolean send(byte[] data, int port, InetAddress address);
	public boolean receive(byte[] data);	

}
