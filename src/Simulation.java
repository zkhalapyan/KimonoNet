import kimononet.geo.GeoLocation;
import kimononet.peer.Peer;
import kimononet.peer.PeerAgent;
import kimononet.peer.address.PeerAddress;


public class Simulation {

	public static void main(String args[]){
		System.out.println("Hello World");
		
		
		Peer peerA = new Peer("111111111111");
		PeerAgent agentA = new PeerAgent(peerA); 
		
		geoLocationTest();
		peerAddressTest();
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
}
