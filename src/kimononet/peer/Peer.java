package kimononet.peer;

import kimononet.geo.GeoLocation;
import kimononet.peer.address.PeerAddress;

/**
 * The Peer object stores information about the peer that is unique to a 
 * specific peer. This information includes the peer's GPS location, 
 * human-readable name, and uniquely identifying peer address.  
 * 
 * @author Zorayr Khalapyan
 *
 */
public class Peer {

	/**
	 * Represents a human readable name for the this peer.
	 */
	private String name;
	
	/**
	 * A unique peer identification address. 
	 */
	private PeerAddress address;
	
	/**
	 * Stores peer's current location.
	 */
	private GeoLocation location;
	
	/**
	 * Creates a peer with the specified unique address and default name.
	 * 
	 * @param address A peer address that uniquely identifies a peer.
	 */
	public Peer(String address){
		this(address, new PeerAddress("00:00:00:00:00:00"));
	}
	
	public Peer(String name, PeerAddress address){
		this.name = name;
		this.address = address;
	}
	
	/**
	 * Sets the peer's current GPS location.
	 * @param location The peer's current GPS location.
	 */
	public void setLocation(GeoLocation location){
		this.location = location; 
	}

	/**
	 * Returns the peer's current GPS location.
	 * @return The peer's current GPS location.
	 */
	public GeoLocation getLocation(){
		return this.location;
	}
	
	/**
	 * Returns the peer's name.
	 * @return The peer's name.
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PeerAddress getAddress() {
		return address;
	}

	public void setAddress(PeerAddress address) {
		this.address = address;
	}
}
