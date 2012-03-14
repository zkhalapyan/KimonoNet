package kimononet.stat;

/**
 * The class encapsulates specific calculated data gathered from analyzing the 
 * data gathered from monitoring packets that are sent from a single source
 * and are delivered to a single sink.
 * 
 * Relevant exposed data will include the following values:
 * 
 * <pre>
 * 1. <b>Packets Lost:</b> Calculated as difference between the number of 
 *    packets that left the source, and the number of packets that arrived at 
 *    the destination.
 * 2. <b>Packet Delivery Percentage:</b> Calculated as the ratio between the 
 *    number of packets received at the sink and the number of packets sent at 
 *    the source.
 * 3. <b>Control Overhead:</b> Calculated as the ratio of the number of 
 *    sent/received beacon packets over the number of sent/received data 
 *    packets.
 * 4. <b>Greedy Ratio:</b> Calculated as the ratio of the number of sent packets
 *    that used greedy over the number of sent packets that used parameter 
 *    routing.
 * </pre>
 * 
 * @author Zorayr Khalapyan
 * @since 3/14/2012
 * @version 3/14/2012
 */
public class StatResults {

	/**
	 * Number of packets that were lost in the path from source to destination.
	 */
	private int lostPackets;
	
	/**
	 * Number of packets sent from the source.
	 */
	private int sentPackets;
	
	/**
	 * Number of beacon packets sent.
	 */
	private int beaconPackets;
	
	/**
	 * Number of packets that were forwarded using the greedy algorithm.
	 */
	private int greedyCount;
	
	/**
	 * Number of packets that were forwarded using the parameter routing 
	 * algorithm.
	 */
	private int parameterCount;
	

	/**
	 * Creates a new results package given various count values.
	 * 
	 * @param lostPackets    The total number of packets lost sent from a single 
	 *                       source to a single destination.
	 *                       
	 * @param sentPackets    The total number of sent packets from the sink to 
	 * 						 the source.
	 * 
	 * @param beaconPackets  The total number of beacon packets sent.
	 * 
	 * @param greedyCount    The total number of packets that used greedy 
	 * 						 routing algorithm.
	 * 
	 * @param parameterCount The total number of packets that used the parameter
	 *  					 routing algorithm.
	 */
	public StatResults(int lostPackets, 
					   int sentPackets, 
					   int beaconPackets, 
					   int greedyCount, 
					   int parameterCount){
		
		this.lostPackets      = lostPackets;
		this.sentPackets      = sentPackets;
		this.beaconPackets    = beaconPackets;
		this.greedyCount      = greedyCount;
		this.parameterCount   = parameterCount;
	}
	

	/**
	 * Return the ratio between the number of packets received at the sink and 
	 * the number of packets sent at the source.
	 * 
	 * @return Packet delivery ratio.
	 */
	public double getPacketDeliveryRatio(){
		return (sentPackets == 0)? 
					0.0 : 
					(sentPackets - lostPackets) / (double)sentPackets;
	}
	
	/**
	 * Returns the ratio of the number of sent/received beacon packets over the 
	 * number of sent/received data packets.
	 * 
	 * @return Control overhead ratio.
	 */
	public double getControlOverhead(){
		return (sentPackets == 0)? 
					0.0 : 
				    (sentPackets - beaconPackets) / (double)sentPackets;
	}
	
	/**
	 * Returns the ratio of the number of sent packets that used greedy over the
	 * number of sent packets that used parameter routing.
	 * 
	 * @return Greedy ratio.
	 */
	public double getGreedyRatio(){
		return (parameterCount == 0)? 
					0.0 : 
					greedyCount / (double)parameterCount;
	}
	
	
	/**
	 * Returns the number of packets that were sent from the source but not 
	 * received at the destination.
	 * 
	 * @return The number of packets lost.
	 */
	public int getLostPackets() {
		return lostPackets;
	}

	/**
	 * Returns the total number of packets sent from the sink to the source.
	 * @return The total number of packets sent from the sink to the source.
	 */
	public int getSentPackets() {
		return sentPackets;
	}

	/**
	 * @return the beaconPackets
	 */
	public int getBeaconPackets() {
		return beaconPackets;
	}

	/**
	 * @return the greedyCount
	 */
	public int getGreedyCount() {
		return greedyCount;
	}

	/**
	 * @return the parameterCount
	 */
	public int getParameterCount() {
		return parameterCount;
	}
	
	
	
}
