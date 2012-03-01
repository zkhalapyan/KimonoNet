package kimononet.net.p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class MulticastConnection implements Connection{

	private MulticastSocket socket;
	
	/**
	 * Buffer byte space for receiving data.
	 */
	private static final byte[] buffer = new byte[Connection.MAX_PACKET_LENGTH]; 
	
	/**
	 * Indicates the state of the socket. From the user perspective, the socket
	 * is "connected", but from the implementation point of view, the socket 
	 * has just been initialized - given the UDP architecture, no connection 
	 * will be made until a packet is send. 
	 */
	private boolean connected = false;
	
	
	public MulticastConnection(int port, String address){
		
		try {
			
			System.out.println(address);
			socket = new MulticastSocket(port);
			socket.joinGroup(InetAddress.getByName(address));
			connected = true;
	
		} catch (UnknownHostException e) {			
			e.printStackTrace();
	
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	/**
	 * 
	 * @param blocking New value to set.
	 */
	@Override
	public boolean setBlocking(boolean blocking){
		return setTimeout((blocking)? 0 : Connection.DEFAULT_TIMEOUT);
	}
	
	@Override
	public boolean setTimeout(int timeout){
		
		if(isConnected()){
			try {
				socket.setSoTimeout(timeout);
				return true;
				
			} catch (SocketException e) {
				e.printStackTrace();
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
	public boolean isConnected(){
		return connected;
	}
	
	@Override
	public boolean disconnect() {
		
		if(!isConnected())
			return true;
		
		try {
			
			socket.leaveGroup(socket.getInetAddress());
			socket.disconnect();
			socket.close();
			
			connected = false;
			
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	
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
