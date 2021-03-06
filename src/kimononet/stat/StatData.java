package kimononet.stat;

import java.util.ArrayList;

import kimononet.log.Logger;
import kimononet.net.Packet;
import kimononet.net.PacketType;
import kimononet.peer.PeerAddress;

/**
 * The class represents the data gathered while sending and receiving packets. 
 * Various methods are included in order to quickly analyze the gathered data. 
 * For example, {@link #getLostPackets(PeerAddress, PeerAddress)} will returned
 * the number of lost packets sent from provided source to the specified sink.
 * 
 * @author Zorayr Khalapyan
 * @since 3/12/2012
 * @version 3/14/2012
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
	
	public void reset(){
		
		synchronized(sentPackets){
			sentPackets.clear();
		}
		
		synchronized(receivedPackets){
			receivedPackets.clear();
		}
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
	 */
	public StatResults getStatResults(PeerAddress sourceAddress, PeerAddress destinationAddress){
		
		int totalPacketCount = 0;
		
		int totalSentPackets = 0;
		int totalReceivedPackets = 0;
		
		int totalSentDataPackets = 0;
		int totalReceivedDataPackets = 0;
		
		int receivedPacketCount = 0;
		int sentPacketCount     = 0;
		int beaconPacketCount   = 0;
		int dataPacketCount     = 0;
		
		
		int greedyCount      = 0;
		int perimeterCount   = 0;
		
		synchronized(sentPackets){
			
			for(StatPacket packet : sentPackets){
				
				if(packet.isDropped()){
					continue;
				}
				
				totalSentPackets ++;
				totalPacketCount ++;
				
				if(packet.getType() == PacketType.BEACON){
					beaconPacketCount ++;
					
				}else if(packet.getType() == PacketType.DATA){
					
					dataPacketCount ++;
					
					totalSentDataPackets ++;
					
					switch(packet.getMode()){
						case GREEDY:
							greedyCount ++;
							break;
						case PERIMETER:
							perimeterCount ++;
							break;
							
					}
					
					
					if(sourceAddress != null 
					   && destinationAddress != null
					   && packet.isSource() 
					   && packet.getSource().equals(sourceAddress)
					   && packet.getDestination().equals(destinationAddress)){
						
						sentPacketCount ++;
						
					}
				}			
			}
		}
		
		synchronized(receivedPackets){
			
			for(StatPacket packet : receivedPackets){
				
				if(packet.isDropped()){
					continue;
				}
				
				totalReceivedPackets++;
				
				totalPacketCount++;
				
				if(packet.getType() == PacketType.DATA){
					
					totalReceivedDataPackets ++;
					
					if(sourceAddress != null 
					   && destinationAddress != null
					   && packet.isSink() 
					   && packet.getSource().equals(sourceAddress)
					   && packet.getDestination().equals(destinationAddress)){
						
						receivedPacketCount ++;
					}
				}
			}
		}
	
		return new StatResults(totalPacketCount,
							   sentPacketCount, 
							   receivedPacketCount, 
							   beaconPacketCount, 
							   dataPacketCount,
							   greedyCount, 
							   perimeterCount,
							   totalSentDataPackets,
							   totalReceivedDataPackets,
							   totalSentPackets,
							   totalReceivedPackets);
		
	}
	
}
