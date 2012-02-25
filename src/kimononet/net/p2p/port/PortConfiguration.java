package kimononet.net.p2p.port;

/**
 * Current peer's port configuration. Includes information such as beacon
 * service port number, and data service port number.
 * 
 * @author Zorayr Khalapyan
 */
public class PortConfiguration {
	
	private int beaconServicePort;
	
	private int dataSendingServicePort;
	
	private int dataReceivingServicePort;
	
	public PortConfiguration(int beaconServicePort, 
							 int dataSendingServicePort,
							 int dataReceivingServicePort){
								 
		this.beaconServicePort = beaconServicePort;
		
		this.dataSendingServicePort = dataSendingServicePort;
		
		this.dataReceivingServicePort = dataReceivingServicePort;
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
