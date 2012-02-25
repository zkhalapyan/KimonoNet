package kimononet.net.p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class UDPConnection implements Connection{
	
	/**
	 * Default timeout for receiving UDP packet.
	 */
	public static final int DEFAULT_UDP_TIMEOUT = 200;
	
	/**
	 * Buffer byte space for recieving data.
	 */
	private static final byte[] buffer = new byte[Connection.MAX_PACKET_LENGTH]; 
		
	/**
	 * UDP connection socket.
	 */
	private DatagramSocket socket;
	
	/**
	 * Indicates the state of the socket.
	 */
	private boolean connected = false;
	
	public UDPConnection(int port){
		
		connected = true;
		
		try {
			this.socket = new DatagramSocket(port);
			
		} catch (SocketException e) {
			e.printStackTrace();
			connected = false;
		}
	}
	
	/**
	 * Creates a new connection with the specified port number and destination 
	 * network address.
	 * 
	 * @param port Destination port address. 
	 * @param address Destination network address.
	 */
	public UDPConnection(int port, InetAddress address){
		
		connected = true;
		
		try {
			this.socket = new DatagramSocket(port, address);
		} catch (SocketException e) {
			
			e.printStackTrace();
			connected = false;
		}
	}
	
	/**
	 * 
	 * @param blocking New value to set.
	 */
	public void setBlocking(boolean blocking){
		try {
			socket.setSoTimeout((blocking)? 0 : DEFAULT_UDP_TIMEOUT);
		} catch (SocketException e) {
			//
		}
	}
	
	public void setTimeout(int timeout){
		try {
			socket.setSoTimeout(timeout);
		} catch (SocketException e) {
			//
		}
	}
	
	@Override
	public boolean connect() {
		return connected;
	}

	@Override
	public boolean disconnect() {
		
		if(socket != null){
			socket.disconnect();
		}
		
		this.connected = false;
		
		return true;
	}

	@Override
	public boolean send(byte[] data, int port, InetAddress address) {
		
		if(!connected){
			return false;
		}
		
		try {
			
			DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
			
			socket.send(packet);
			
			return true;
			
		} catch(SocketTimeoutException ex){
			
			ex.printStackTrace();
			return false;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
			
		}
		
	}

	@Override
	public byte[] receive() {

		if(!connected){
			throw new ConnectionException("Socket disconnected - data cannot be received.");
		}
		
        try {
        	
        	//Create a packet to receive data into the buffer.
            DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            	
            //Receive the packet - if the timeout runs out, then this method will
            //throw an exception.
			socket.receive(receivedPacket);
			
			byte[] data = new byte[receivedPacket.getLength()];
			
			System.arraycopy(receivedPacket.getData(), 0, data, 0, data.length);
			
			return data;
			
			
		} catch(SocketTimeoutException ex){
			  return null;
						
		} catch (IOException e) {
			return null;
		}
        
	}

	
}
