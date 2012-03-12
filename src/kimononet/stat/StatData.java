package kimononet.stat;

import java.util.ArrayList;

import kimononet.net.Packet;
import kimononet.peer.PeerAddress;

/**
 * The class represents the data gathered while sending and receiving packets. 
 * Various methods are included in order to quickly analyze the gathered data. 
 * For example, {@link #getLostPackets(PeerAddress, PeerAddress)} will returned
 * the number of lost packets sent from provided source to the specified sink.
 * 
 * @author Zorayr Khalapyan
 * @since 3/12/2012
 * @version 3/12/2012
 *
 */
public class StatData {

	/**
	 * Initial capacity for {@link #sentPackets} and {@link #receivedPackets}.
	 */
	private static final int DEFAULT_LIST_CAPACITY = 100;
	
	/**
	 * Stores any packets that was sent. To add packets to this list use 
	 * {@link #addSentPacket(Packet)}.
	 */
	private final ArrayList<StatPacket> sentPackets;
	
	/**
	 * Stores any packets that was received. To add packets to this list use 
	 * {@link #addReceivedPacket(Packet)}.
	 */
	private final ArrayList<StatPacket> receivedPackets;
	
	/**
	 * Creates an empty data set.
	 */
	public StatData(){
		sentPackets      = new ArrayList<StatPacket>(DEFAULT_LIST_CAPACITY);
		receivedPackets = new ArrayList<StatPacket>(DEFAULT_LIST_CAPACITY);
	}
	
	/**
	 * Adds a packet to the sent packets list.
	 * @param packet Sent packet.
	 */
	public final void addSentPacket(StatPacket packet){
		
		synchronized(sentPackets){
			sentPackets.add(packet);
		}
		
	}
	
	/**
	 * Adds a packet to the received packets list.
	 * @param packet Received packet.
	 */
	public final void addReceivedPacket(StatPacket packet){
		
		synchronized(receivedPackets){
			receivedPackets.add(packet);
		}
		
	}
	
	/**
	 * ToDo:Implemnet this method.
	 * 
	 * @param sourceAddress
	 * @param destinationAddress
	 * @return
	 */
	public int getLostPackets(PeerAddress sourceAddress, PeerAddress destinationAddress){
		
		int lostPackets = 0;
		
		
		return lostPackets;
	}
	
}
