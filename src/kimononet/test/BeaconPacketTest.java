package kimononet.test;

import static org.junit.Assert.*;
import kimononet.geo.GeoLocation;
import kimononet.net.p2p.BeaconPacket;
import kimononet.net.p2p.BeaconType;
import kimononet.peer.Peer;
import kimononet.peer.PeerAgent;

import org.junit.Before;
import org.junit.Test;

public class BeaconPacketTest {

	private byte[] byteArray = {	(byte)0xF0, (byte)0x0B,																				// BEACON_PACKET_MAGIC_FLAG
									(byte)0x01,																							// BEACON_PACKET_VERSION
									(byte)0x01,																							// beacon type
									(byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67, (byte)0x89, (byte)0xAB,								// address
									(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,		// timestamp (dynamic, won't be checked)
									(byte)0xC0, (byte)0x53, (byte)0x4B, (byte)0x8E, (byte)0x36, (byte)0x8F, (byte)0x08, (byte)0x46,		// longitude (big-endian)
									(byte)0x40, (byte)0x43, (byte)0x8D, (byte)0x80, (byte)0x5E, (byte)0x5F, (byte)0x30, (byte)0xE8,		// latitude (big-endian)
									(byte)0x40, (byte)0x5E, (byte)0xDD, (byte)0x3C, (byte)0x07, (byte)0xEE, (byte)0x0B, (byte)0x0B};	// accuracy (big-endian)

	private BeaconPacket beacon;

	@Before
	public void test_BeaconPacket() {
		Peer peer = new Peer("01:23:45:67:89:AB");
		peer.setLocation(new GeoLocation(-77.180555, 39.105480, 123.4567890));
		PeerAgent agent = new PeerAgent(peer);
		beacon = new BeaconPacket(agent);
		beacon.setType(BeaconType.BEACON);
	}

	@Test
	public void test_toByteArray() {
		// Skip timestamp, i.e. only check bytes 0-9, 18-41.

		final int offset1 = 0;
		final int length1 = 10;
		final int offset2 = 18;
		final int length2 = 24;

		byte[] byteArrayExpected1 = new byte[length1];
		byte[] byteArrayExpected2 = new byte[length2];

		byte[] byteArrayActual1 = new byte[length1];
		byte[] byteArrayActual2 = new byte[length2];

		System.arraycopy(byteArray, offset1, byteArrayExpected1, 0, length1);
		System.arraycopy(byteArray, offset2, byteArrayExpected2, 0, length2);

		System.arraycopy(beacon.toByteArray(), offset1, byteArrayActual1, 0, length1);
		System.arraycopy(beacon.toByteArray(), offset2, byteArrayActual2, 0, length2);

		assertArrayEquals(byteArrayExpected1, byteArrayActual1);
		assertArrayEquals(byteArrayExpected2, byteArrayActual2);
	}

}
