package kimononet.test;

public class TestManager {

	public void startTesting() {
		// Test Parcel class.
		(new ParcelTest()).testAddByte();
		(new ParcelTest()).testAddByteArray();
		(new ParcelTest()).testAddChar();
		(new ParcelTest()).testAddDouble();
		(new ParcelTest()).testAddFloat();
		(new ParcelTest()).testAddInt();
		(new ParcelTest()).testAddIntLong();
		(new ParcelTest()).testAddLong();
		(new ParcelTest()).testAddParcelable();
		(new ParcelTest()).testAddString();
		(new ParcelTest()).testCapacity();
		(new ParcelTest()).testCombineParcelables();
		(new ParcelTest()).testCompact();
		(new ParcelTest()).testCopy();
		(new ParcelTest()).testGetByte();
		(new ParcelTest()).testGetByteArray();
		(new ParcelTest()).testGetChar();
		(new ParcelTest()).testGetDouble();
		(new ParcelTest()).testGetFloat();
		(new ParcelTest()).testGetInt();
		(new ParcelTest()).testGetLong();
		(new ParcelTest()).testGetParcelSize();
		(new ParcelTest()).testRewind();
		(new ParcelTest()).testSetParcel();
		(new ParcelTest()).testSlice();
		(new ParcelTest()).testToByteArray();
		(new ParcelTest()).testToParcel();

		// Test GeoLocation class.
		(new GeoLocationTest()).testGetAccuracy();
		(new GeoLocationTest()).testGetLatitude();
		(new GeoLocationTest()).testGetLongitude();
		(new GeoLocationTest()).testGetParcelSize();
		(new GeoLocationTest()).testGetTimestamp();
		(new GeoLocationTest()).testParse();
		(new GeoLocationTest()).testSetLocationDoubleDoubleFloat();
		(new GeoLocationTest()).testSetLocationParcel();
		(new GeoLocationTest()).testSetTimestamp();
		(new GeoLocationTest()).testToParcel();
		(new GeoLocationTest()).testToString();

		// Test GeoVelocity class.
		(new GeoVelocityTest()).testGetAverageAccuracy();
		(new GeoVelocityTest()).testGetBearing();
		(new GeoVelocityTest()).testGetInitialBearing();
		(new GeoVelocityTest()).testGetParcelSize();
		(new GeoVelocityTest()).testGetSpeed();
		(new GeoVelocityTest()).testParse();
		(new GeoVelocityTest()).testToParcel();
		(new GeoVelocityTest()).testToString();
		(new GeoVelocityTest()).testUpdate();

		// Test PeerAddress class.
		(new PeerAddressTest()).testEquals();
		(new PeerAddressTest()).testGetParcelSize();
		(new PeerAddressTest()).testHashCode();
		(new PeerAddressTest()).testSetAddress();
		(new PeerAddressTest()).testToParcel();
		(new PeerAddressTest()).testToString();

		// Test Peer class.
		(new PeerTest()).testGetAddress();
		(new PeerTest()).testGetLocation();
		(new PeerTest()).testGetName();
		(new PeerTest()).testGetParcelSize();
		(new PeerTest()).testGetVelocity();
		(new PeerTest()).testParse();
		(new PeerTest()).testSetAddress();
		(new PeerTest()).testSetLocation();
		(new PeerTest()).testSetName();
		(new PeerTest()).testToParcel();

		// Test PeerEnvironment class.
		(new PeerEnvironmentTest()).testPeerEnvironment();

		// Test PeerAgent class.
		(new PeerAgentTest()).testGetEnvironment();
		(new PeerAgentTest()).testGetGeoDevice();
		(new PeerAgentTest()).testGetPeer();
		(new PeerAgentTest()).testGetPeers();
		(new PeerAgentTest()).testGetPeers2();
		(new PeerAgentTest()).testGetPortConfiguration();
		(new PeerAgentTest()).testGetTime();
		(new PeerAgentTest()).testGetTimeProvider();
		(new PeerAgentTest()).testSetEnvironment();
		(new PeerAgentTest()).testSetTimeProvider();
		(new PeerAgentTest()).testShutdownServices();
		(new PeerAgentTest()).testStartServices();

		// Test Packet class.
		(new PacketTest()).testGetContents();
		(new PacketTest()).testGetContentsLength();
		(new PacketTest()).testGetParcelSize();
		(new PacketTest()).testGetPeer();
		(new PacketTest()).testGetType();
		(new PacketTest()).testParse();
		(new PacketTest()).testSetContents();
		(new PacketTest()).testSetType();
		(new PacketTest()).testToParcel();
		(new PacketTest()).testToString();

		// TODO: Test BeaconPacket class.

		(new UDPConnectionTest()).testUDPConnection();
	}

}
