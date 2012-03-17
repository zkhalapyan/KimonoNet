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
	 * Number of packets sent from the source.
	 */
	private int sentPackets;
	
	/**
	 * Number of packets received at the destination.
	 */
	private int receivedPackets;
	
	/**
	 * Number of sent beacon packets.
	 */
	private int beaconPackets;
	
	/**
	 * Number of sent data packets.
	 */
	private int dataPackets;
	
	/**
	 * Number of packets that were forwarded using the greedy algorithm.
	 */
	private int greedyCount;
	
	/**
	 * Number of packets that were forwarded using the parameter routing 
	 * algorithm.
	 */
	private int perimeterCount;
	

	/**
	 * Creates a new results package given various count values.
	 * 
	 *                       
	 * @param sentPackets      The total number of sent packets from the sink to 
	 * 						   the source.
	 * 
	 * @param recievedPackets The total number of packets received at the sink 
	 *                        sent from a single source.
	 * 
	 * @param beaconPackets   The total number of beacon packets sent.
	 * 
	 * @param dataPackets     The total number of data packets sent.
	 * 
	 * @param greedyCount     The total number of packets that used greedy 
	 * 						  routing algorithm.
	 * 
	 * @param perimeterCount  The total number of packets that used the parameter
	 *  					  routing algorithm.
	 */
	public StatResults(int sentPackets,
					   int receivedPackets,
					   int beaconPackets, 
					   int dataPackets,
					   int greedyCount, 
					   int perimeterCount){
		
		this.sentPackets     = sentPackets;
		this.receivedPackets = receivedPackets;
		this.beaconPackets   = beaconPackets;
		this.dataPackets     = dataPackets;
		this.greedyCount     = greedyCount;
		this.perimeterCount  = perimeterCount;
	}
	

	/**
	 * Creates a new results package with all values set to null.
	 */
	public StatResults() {
		this(0, 0, 0, 0, 0, 0);
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
					(receivedPackets) / (double)sentPackets;
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
		return (dataPackets == 0)? 
					0.0 : 
					greedyCount / (double)dataPackets;
	}
	
	
	/**
	 * Returns the number of packets that were sent from the source but not 
	 * received at the destination.
	 * 
	 * @return The number of packets lost.
	 */
	public int getLostPackets() {
		return sentPackets - receivedPackets;
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
	 * @return the perimeterCount
	 */
	public int getPerimeterCount() {
		return perimeterCount;
	}
	
	/**
	 * Combines the specified results with the current results.
	 * @param results The results to combine.
	 */
	public void combine(StatResults results){
		this.receivedPackets += results.receivedPackets;
		this.sentPackets     += results.sentPackets;
		this.beaconPackets   += results.beaconPackets;
		this.dataPackets     += results.dataPackets;
		this.greedyCount     += results.greedyCount;
		this.perimeterCount  += results.perimeterCount;
	}
	
	
	
	
}
