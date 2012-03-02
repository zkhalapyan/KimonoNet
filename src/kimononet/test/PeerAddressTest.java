package kimononet.test;

import static org.junit.Assert.*;
import kimononet.peer.PeerAddress;

import org.junit.Before;
import org.junit.Test;

public class PeerAddressTest {

	private final String addressString = "01:23:45:67:89:AB";
	private final byte[] addressByteArray  = {(byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67, (byte)0x89, (byte)0xAB};

	private final String addressString2 = "AB:CD:EF:01:23:45";
	//private final byte[] addressByteArray2 = {(byte)0xAB, (byte)0xCD, (byte)0xEF, (byte)0x01, (byte)0x23, (byte)0x45};

	private PeerAddress peerAddressByteArray;
	private PeerAddress peerAddressString;

	public PeerAddressTest() {
		testPeerAddressByteArray();
		testPeerAddressString();
	}

	@Before
	public void testPeerAddressByteArray() {
		peerAddressByteArray = new PeerAddress(addressByteArray);
	}

	@Before
	public void testPeerAddressString() {
		peerAddressString = new PeerAddress(addressString);
	}

	@Test
	public void testSetAddress() {
		peerAddressByteArray.setAddress(addressString2);
		peerAddressString.setAddress(addressString2);
		assertEquals(addressString2, peerAddressByteArray.toString());
		assertEquals(addressString2, peerAddressString.toString());
	}

	@Test
	public void testToString() {
		assertEquals(addressString, peerAddressByteArray.toString());
		assertEquals(addressString, peerAddressString.toString());
	}

}
