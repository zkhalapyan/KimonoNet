package kimononet.net.p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public class UDPConnection implements Connection{
	
	/**
	 * Buffer byte space for receiving data.
	 */
	private static final byte[] buffer = new byte[Connection.MAX_PACKET_LENGTH]; 
		
	/**
	 * UDP connection socket.
	 */
	private DatagramSocket socket;
	
	/**
	 * Indicates the state of the socket. From the user perspective, the socket
	 * is "connected", but from the implementation point of view, the socket 
	 * has just been initialized - given the UDP architecture, no connection 
	 * will be made until a packet is send. 
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
	public UDPConnection(int port, String address){
		
		connected = true;
		
		try {
			
			InetAddress inetAddress = InetAddress.getByName(address);
			this.socket = new DatagramSocket(port, inetAddress);
			
		} catch (SocketException e) {
			e.printStackTrace();
			connected = false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			connected = false;
		}
	}
	
	
	@Override
	public boolean isConnected(){
		return connected;
	}
	
	/**
	 * 
	 * @param blocking New value to set.
	 */
	@Override
	public boolean setBlocking(boolean blocking){
		return setTimeout((blocking)? 0 : Connection.DEFAULT_TIMEOUT);
	}
	
	public boolean setTimeout(int timeout){
		
		if(isConnected()){
			try {
				socket.setSoTimeout(timeout);
				return true;
			} catch (SocketException e) {
				return false;
			}
		}
		
		return false;
		
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
	public boolean send(byte[] data, int port, String address) {
		
		if(!connected){
			return false;
		}
		
		try {
			
			socket.send(new DatagramPacket(data, 
					                       data.length, 
					                       InetAddress.getByName(address), 
					                       port));
			
			return true;
			
		} catch(SocketTimeoutException ex){
			ex.printStackTrace();
			return false;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
			
		}
		
	}

	@Override
	public byte[] receive() {

		if(!isConnected()){
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
