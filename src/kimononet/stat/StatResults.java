package kimononet.stat;

import kimononet.log.Logger;

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
 * @version 3/18/2012
 */
public class StatResults {

	/**
	 * A count of all the packets sent and received.
	 */
	private int totalPacketCount;
	
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
	
	private int totalSentDataPackets;
	
	private int totalReceivedDataPackets;
	
	private int totalSentPackets;
	
	private int totalReceivedPackets;
	

	/**
	 * Creates a new results package given various count values.
	 * 
	 *                       
	 * @param totalPacketCount The total number of all the packets sent and 
	 * 						   received. 
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
	 * @param perimeterCount  The total number of packets that used the 
	 * 						  parameter routing algorithm.
	 */
	public StatResults(int totalPacketCount,
					   int sentPackets,
					   int receivedPackets,
					   int beaconPackets, 
					   int dataPackets,
					   int greedyCount, 
					   int perimeterCount,
					   int totalSentDataPackets,
					   int totalReceivedDataPackets,
					   int totalSentPackets,
					   int totalReceivedPackets){
		
		this.totalPacketCount = totalPacketCount;
		this.sentPackets      = sentPackets;
		this.receivedPackets  = receivedPackets;
		this.beaconPackets    = beaconPackets;
		this.dataPackets      = dataPackets;
		this.greedyCount      = greedyCount;
		this.perimeterCount   = perimeterCount;
		
		this.totalSentDataPackets = totalSentDataPackets;
		this.totalReceivedDataPackets = totalSentDataPackets;
		
		this.totalSentPackets = totalSentPackets;
		this.totalReceivedPackets = totalReceivedPackets;
	}
	

	/**
	 * Creates a new results package with all values set to null.
	 */
	public StatResults() {
		this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
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
	 * Returns the ratio of the number of sent beacon packets over the 
	 * number of the total sent/received data packets. In other words, returns
	 * ({@link #beaconPackets}/{@link #totalPacketCount}). 
	 * 
	 * @return Control overhead ratio.
	 */
	public double getControlOverhead(){
		return (totalSentPackets == 0)? 
					0.0 : 
				    (beaconPackets) / (double)totalSentPackets;
	}
	
	/**
	 * Returns the ratio of the number of sent packets that used greedy over the
	 * number of sent packets that used parameter routing.
	 * 
	 * @return Greedy ratio.
	 */
	public double getGreedyRatio(){
		return (totalSentDataPackets == 0)? 
					0.0 : 
					greedyCount / (double)totalSentDataPackets;
	}
	
	
	/**
	 * Returns the total number of packets sent or received.
	 * @return the total number of packets sent or received.
	 * @see #totalPacketCount
	 */
	public int getTotalPacketCount(){
		return this.totalPacketCount;
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
	 * @see #sentPackets
	 */
	public int getSentPackets() {
		return sentPackets;
	}

	/**
	 * Returns the number of beacon packets that were sent.
	 * @return The total number of sent beacon packets.
	 * @see #beaconPackets
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
		this.totalPacketCount += results.totalPacketCount;
		this.receivedPackets  += results.receivedPackets;
		this.sentPackets      += results.sentPackets;
		this.beaconPackets    += results.beaconPackets;
		this.dataPackets      += results.dataPackets;
		this.greedyCount      += results.greedyCount;
		this.perimeterCount   += results.perimeterCount;
		this.totalReceivedDataPackets += results.totalReceivedDataPackets;
		this.totalSentDataPackets += results.totalSentDataPackets;
		
		this.totalSentPackets     += results.totalSentPackets;
		this.totalReceivedPackets += results.totalReceivedPackets;
	}
	
	public int getTotalSentPackets(){
		return this.totalSentPackets;
	}
	
	public int getTotalReceivedPackets(){
		return this.totalReceivedPackets;
	}
	
	/**
	 * Returns a string representation of the current results. The output 
	 * includes the following information.
	 * 
	 * <pre>
	 * 1. <b>Packets Lost:</b> Calculated as difference between the number of 
	 *    packets that left the source, and the number of packets that arrived 
	 *    at the destination.
	 * 2. <b>Packet Delivery Percentage:</b> Calculated as the ratio between the 
	 *    number of packets received at the sink and the number of packets sent 
	 *    at the source.
	 * 3. <b>Control Overhead:</b> Calculated as the ratio of the number of 
	 *    sent beacon packets over the number of total packets.
	 * 4. <b>Greedy Ratio:</b> Calculated as the ratio of the number of sent 
	 * 	  packets that used greedy over the number of sent packets that used 
	 * 	  parameter routing.
	 * </pre>
	 */
	public String toString(){
		
		String results = "";
		
		results += "################# RESULTS ################"        + "\n";
		results += "Lost Packets:          \t" +  getLostPackets()     + "\n";
		results += "Packet Delivery Ratio: \t" + (int)(getPacketDeliveryRatio() * 100)    + "%\n";
		results += "Control Overhead:      \t" + (int)(getControlOverhead() * 100)    + "%\n";
		results += "Greedy Ratio:          \t" + (int)(getGreedyRatio()     * 100)    + "%\n\n";
		
		
		results += "################## COUNTS ################"                 + "\n";
		
		results += "Total Packet Count:                      \t" + totalPacketCount         + "\n";
		results += "Total Sent Packets:                      \t" + totalSentPackets         + "\n";
		results += "Total Received Packets:                  \t" + totalReceivedPackets     + "\n";
		results += "Total Sent Data Packet Count:            \t" + totalSentDataPackets     + "\n";
		results += "Total Received Data Packet Count:        \t" + totalReceivedDataPackets + "\n";
		results += "Sent Packets (from source) Count:        \t" + sentPackets 	            + "\n";
		results += "Received Packets (at destination) Count: \t" + receivedPackets          + "\n";
		results += "Beacon Packet Count:                     \t" + beaconPackets            + "\n";
		
		results += "Data Packet Count:       \t" + dataPackets 	          + "\n";
		results += "Greedy Routing Count:    \t" + greedyCount 	          + "\n";
		results += "Perimeter Routing Count: \t" + perimeterCount         + "\n\n";
		
		return results;
	}
	
}
