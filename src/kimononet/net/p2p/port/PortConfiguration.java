package kimononet.net.p2p.port;

/**
 * Current peer's port configuration. Includes information such as beacon
 * service port number, and data service port number.
 * 
 * @author Zorayr Khalapyan
 */
public class PortConfiguration {
	
	/**
	 * Local source address.
	 */
	private String address;
	
	/**
	 * Indicates whether this port is a bound to a multicast socket.
	 */
	private boolean isMulticast;
	
	private int beaconServicePort;
	
	private int dataSendingServicePort;
	
	private int dataReceivingServicePort;
	
	public PortConfiguration(String address,
							 int beaconServicePort, 
							 int dataSendingServicePort,
							 int dataReceivingServicePort, 
							 boolean isMulticast){
							
		this.address                  = address;
		this.beaconServicePort        = beaconServicePort;
		this.dataSendingServicePort   = dataSendingServicePort;
		this.dataReceivingServicePort = dataReceivingServicePort;
		this.isMulticast              = isMulticast;
    }
	
	public String getAddress(){
		return address;
	}

	public boolean isMulticast(){
		return isMulticast;
	}
	
	public int getBeaconServicePort() {
		return beaconServicePort;
	}

	public int getDataSendingServicePort() {
		return dataSendingServicePort;
	}

	public int getDataReceivingServicePort() {
		return dataReceivingServicePort;
	}
							 
}
