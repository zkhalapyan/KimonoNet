import java.net.InetAddress;
import java.net.UnknownHostException;

import kimononet.geo.GeoLocation;
import kimononet.net.p2p.UDPConnection;
import kimononet.peer.Peer;
import kimononet.peer.PeerAgent;
import kimononet.peer.address.PeerAddress;


public class Simulation {

	public static void main(String args[]){
		System.out.println("Hello World");
		
		PeerAgent agentA = new PeerAgent(new Peer("12:00:00:00:00:00")); 
		
		agentA.startServices();
		
		
		//geoLocationTest();
		//peerAddressTest();
		//connectionTest();
	}
	
	public static void geoLocationTest(){
		GeoLocation location1 = new GeoLocation(1.0, 2.0, 3.0);		
		GeoLocation location2 = new GeoLocation(4.0, 5.0, 6.0);
		
		System.out.println(location2);
		location2.setLocation(location1.toByteArray());
		System.out.println(location2);
	}
	
	public static void peerAddressTest(){
		
		PeerAddress address = new PeerAddress("12:00:00:00:00:00");
		
		System.out.println(address);
	}
	
	public static void connectionTest(){
		
		final int clientPort = 43210;
		final String hostAddress = "255.255.255.255";
		final String clientAddress = "0.0.0.0";
		
		Thread server = new Thread(){
			public void run(){
				
				UDPConnection server = new UDPConnection(5443);
				server.connect();
				
				while(true){
					
					try {
						
						GeoLocation location = new GeoLocation(1.0, 2.0, 3.0);		
						
						System.out.println("Sending data...");
						server.send(location.toByteArray(), clientPort, InetAddress.getByName(hostAddress));
						System.out.println("Sent data!");
						
						sleep(1000);
				
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
						
					} catch (UnknownHostException e) {
						e.printStackTrace();
						break;
					}
					
					
				}
				
			}
		};
		
		Thread client = new Thread(){
			
			public void run(){
				
				try {
					
					UDPConnection client = new UDPConnection(clientPort, InetAddress.getByName(clientAddress));
				
					client.connect();
					client.setBlocking(true);
					while(true){
					
						try{
							
							System.out.println("Receiving data...");
							byte[] buffer = new byte[32];
							
							if(client.receive(buffer)){
								System.out.println("Data Received! \t" + new GeoLocation(buffer));
							}else{
								System.out.println("Data not received!");
							}
							
							sleep(1000);
							
						}catch (InterruptedException e) {
							e.printStackTrace();
							break;
						}
					
					}
					
				} catch (UnknownHostException e) {
					
					e.printStackTrace();
					
				} 
				
				
			}
		};
		
		server.start();
		client.start();
		
		
		
	}
}
