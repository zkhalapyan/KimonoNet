package kimononet.peer;

import kimononet.geo.GeoLocation;
import kimononet.geo.GeoVelocity;
import kimononet.peer.address.PeerAddress;

/**
 * The Peer object stores information about the peer that is unique to a 
 * specific peer. This information includes the peer's GPS location, velocity,
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
	 * Stores peer's current velocity as calculated by change of GPS location 
	 * over time.
	 */
	private GeoVelocity velocity;
	
	/**
	 * Creates a peer with the specified unique address and default name.
	 * 
	 * @param address A peer address that uniquely identifies a peer.
	 */
	public Peer(String address){
		this(new PeerAddress(address), "unnamed-peer");
	}
	
	/**
	 * Creates a new peer from the specified name and address.
	 * 
	 * @param name Peer's name.
	 * @param address Peer's unique address.
	 */
	public Peer(PeerAddress address, String name){
		this.name = name;
		this.address = address;
		this.velocity = new GeoVelocity();
	}
	
	/**
	 * Sets the peer's current GPS location and updates the velocity.
	 * @param location The peer's current GPS location.
	 */
	public void setLocation(GeoLocation newLocation){
		this.location = newLocation; 
		this.velocity.updateLocation(newLocation);
	}
	
	/**
	 * Returns peer's current velocity. 
	 * @return Peer's current velocity.
	 */
	public GeoVelocity getVelocity(){
		return this.velocity;
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

	/**
	 * Sets the peer's name.
	 * @param name Peer's name.
	 */
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
