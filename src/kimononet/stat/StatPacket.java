package kimononet.stat;

import kimononet.net.PacketType;
import kimononet.net.beacon.BeaconPacket;
import kimononet.net.routing.ForwardMode;
import kimononet.net.transport.DataPacket;
import kimononet.peer.PeerAddress;

/**
 * Represents a shallow copy of a packet. This is useful in situations where we 
 * are only interested in information relevant to gathering statistical 
 * information. For creating statistical packets from either beacon packets or 
 * data packets use {@link #StatPacket(BeaconPacket)} or 
 * {@link #StatPacket(DataPacket, PeerAddress)} accordingly. 
 * 
 * 
 * @author Zorayr Khalapyan
 * @since 3/12/2012
 * @version 3/14/2012
 *
 */
public class StatPacket {

	/**
	 * The type of the current packet. This is important for calculating the 
	 * ratio of overhead vs. data packets processed.
	 */
	private final PacketType type;
	
	/**
	 * The source of the initial packet. This is the node that initially sent
	 * the packet. This value is constant across the life cycle of the packet.
	 */
	private final PeerAddress source;
	
	/**
	 * The destination address of this packet. This value should remain constant
	 * across the life cycle of the packet.
	 */
	private final PeerAddress destination;
	
	/**
	 * The node handling the packet. This might either be the source, the 
	 * destination node, or another intermediary node. 	
	 */
	private final PeerAddress node;
	
	/**
	 * In case the packet is a data node, it will be associated with a routing 
	 * mode which is either GREEDY or PERIMETER;
	 */
	private final ForwardMode mode;
	
	/**
	 * Creates a new packet with the specified values.
	 * 
	 * @param type        The type of the packet.
	 * @param source      The original source of the packet. This value should 
	 *                    be constant across the life cycle of the packet.
	 * @param destination The destination address of the packet. 
	 * @param node        The node handling the packet.
	 * @param mode		  Mode of forwarding. If the packet is not a data 
	 *                    packet,this value should be null.
	 */
	public StatPacket(PacketType type, 
					  PeerAddress source, 
					  PeerAddress destination, 
					  PeerAddress node,
					  ForwardMode mode){
		
		this.type        = type;
		this.source      = source;
		this.destination = destination;
		this.node        = node;
		this.mode 		 = mode;
		
	}
	
	/**
	 * Creates a new statistical packet based on the provided beacon packet. 
	 * Since the beacon packet does not have a specified destination address,
	 * this field will be set to null. Furthermore, {@link #source} and 
	 * {@link #node} will be both set to the address of the peer represented by
	 * the beacon packet.
	 * 
	 * @param beaconPacket The packet to use to create the shallow copy 
	 * 					   statistical packet.
	 */
	public StatPacket(BeaconPacket beaconPacket){
		this(beaconPacket.getType(), 
		     beaconPacket.getPeer().getAddress(),
		     null,
		     beaconPacket.getPeer().getAddress(),
		     null);
	}
	
	/**
	 * Creates a new statistical packet based on the provided statistical 
	 * packet. 
	 * 
	 * @param dataPacket The packet to use to create the shallow statistical
	 *                   packet. Information used will include type, peer 
	 *                   address, and destination peer address.
	 *                   
	 * @param node The node handling the packet. This might be either the 
	 * 			   source, the destination, or an intermediary node.
	 */
	public StatPacket(DataPacket dataPacket, PeerAddress node){
		this(dataPacket.getType(), 
			 dataPacket.getPeer().getAddress(),
			 dataPacket.getDestinationPeer().getAddress(),
			 node,
			 dataPacket.getForwardMode());
	}

	/**
	 * Returns true if the source of the current node is equal to the node
	 * handling the current packet; otherwise, the method will return false.
	 * 
	 * @return True if {@link #node} is the same as {@link #source}.
	 */
	public boolean isSource(){
		return this.source == this.node;
	}
	
	/**
	 * Returns true if the destination of the current node is equal to the node
	 * handling the current packet; otherwise, the method will return false.
	 * 
	 * @return True if {@link #node} is the same as #destination.
	 * 
	 */
	public boolean isSink(){
		return this.destination == this.node;
	}
	
	/**
	 * Returns the type of the packet.
	 * @return the type
	 */
	public PacketType getType() {
		return type;
	}

	/**
	 * Returns the source of the packet.
	 * @return the source
	 */
	public PeerAddress getSource() {
		return source;
	}

	/**
	 * Returns the destination of the packet.
	 * @return The destination of the current packet.
	 */
	public PeerAddress getDestination() {
		return destination;
	}

	/**
	 * Returns the node handling the current packet.
	 * @return The node handling the current packet.
	 */
	public PeerAddress getNode() {
		return node;
	}

	/**
	 * Returns the mode of forwarding.
	 * @return The mode of forwarding.
	 */
	public ForwardMode getMode(){
		return mode;
	}

	

}
