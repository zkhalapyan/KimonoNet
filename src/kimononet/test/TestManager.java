package kimononet.test;

public class TestManager {

	public void startTesting() {
		// Test Parcel class.
		(new ParcelTest()).testAddByte();
		(new ParcelTest()).testAddDouble();
		(new ParcelTest()).testAddInt();
		(new ParcelTest()).testAddFloat();
		(new ParcelTest()).testAddLong();
		(new ParcelTest()).testAddString();
		(new ParcelTest()).testAddByteArray();
		(new ParcelTest()).testAddParcelable();
		(new ParcelTest()).testToByteArray();
		(new ParcelTest()).testToParcel();
		(new ParcelTest()).testSetParcel();
		(new ParcelTest()).testGetParcelSize();
		(new ParcelTest()).testCapacity();
		(new ParcelTest()).testCombineParcelables();

		// Test PeerAddress class.
		(new PeerAddressTest()).testSetAddress();
		(new PeerAddressTest()).testToString();

		// Test PeerAgent class.
		(new PeerAgentTest()).testGetPeers();
		(new PeerAgentTest()).testGetPortConfiguration();
		(new PeerAgentTest()).testGetGeoDevice();
		(new PeerAgentTest()).testGetPeer();
		(new PeerAgentTest()).testGetEnvironment();
		(new PeerAgentTest()).testSetEnvironment();

		// Test PeerEnvironment class.
		(new PeerEnvironmentTest()).testPeerEnvironment();

		// Test Peer class.
		(new PeerTest()).testSetLocation();
		(new PeerTest()).testGetVelocity();
		(new PeerTest()).testGetLocation();
		(new PeerTest()).testGetName();
		(new PeerTest()).testSetName();
		(new PeerTest()).testGetAddress();
		(new PeerTest()).testSetAddress();

		// TODO: PortConfigurationTest, UDPConnectionTest, etc...
	}

}
