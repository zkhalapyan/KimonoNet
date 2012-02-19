package kimononet.test;

import static org.junit.Assert.*;
import kimononet.geo.GeoLocation;
import kimononet.peer.Peer;
import kimononet.peer.address.PeerAddress;

import org.junit.Before;
import org.junit.Test;

public class PeerTest {

	private final String nameDefault = "unnamed-peer";

	private final String address = "01:23:45:67:89:AB";
	private final String name = "Foo Bar Baz";
	//private final GeoLocation location = null;

	private final String address2 = "AB:CD:EF:01:23:45";
	private final String name2 = "asdf zxcv";
	private final GeoLocation location2 = new GeoLocation(-77.180555, 39.105480, 123.4567890);

	private Peer peerFromAddress;
	private Peer peerFromAddressAndName;

	@Before
	public void test_Peer_String() {
		peerFromAddress = new Peer(address);
	}

	@Before
	public void test_Peer_PeerAddressString() {
		peerFromAddressAndName = new Peer(new PeerAddress(address), name);
	}

	private void test_getAddress(String addressExpected) {
		assertEquals(addressExpected, peerFromAddress.getAddress().toString());
		assertEquals(addressExpected, peerFromAddressAndName.getAddress().toString());
	}

	@Test
	public void test_getAddress() {
		test_getAddress(address);
	}

	@Test
	public void test_getName() {
		assertEquals(nameDefault, peerFromAddress.getName());
		assertEquals(name, peerFromAddressAndName.getName());
	}

	@Test
	public void test_getLocation() {
		assertNull(peerFromAddress.getLocation());
		assertNull(peerFromAddressAndName.getLocation());
	}

	@Test
	public void test_setAddress() {
		peerFromAddress.setAddress(new PeerAddress(address2));
		peerFromAddressAndName.setAddress(new PeerAddress(address2));
		test_getAddress(address2);
	}

	@Test
	public void test_setName() {
		peerFromAddress.setName(name2);
		peerFromAddressAndName.setName(name2);
		assertEquals(name2, peerFromAddress.getName());
		assertEquals(name2, peerFromAddressAndName.getName());
	}

	@Test
	public void test_setLocation() {
		peerFromAddress.setLocation(location2);
		peerFromAddressAndName.setLocation(location2);
		assertEquals(location2, peerFromAddress.getLocation());
		assertEquals(location2, peerFromAddressAndName.getLocation());
	}

}
