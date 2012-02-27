package kimononet.test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import kimononet.geo.GeoLocation;
import kimononet.geo.GeoVelocity;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;

import org.junit.Before;
import org.junit.Test;

public class PeerTest {

	private static final String nameDefault = "unnamed-peer";

	private static final String address = "01:23:45:67:89:AB";
	private static final String name = "Foo Bar Baz";
	//private static final GeoLocation location = null;

	private static final String address2 = "AB:CD:EF:01:23:45";
	private static final String name2 = "asdf zxcv";
	private static final GeoLocation location2 = new GeoLocation(-77.180555, 39.105480, (float)123.4567890);

	private static Peer peerString;
	private static Peer peerPeerAddressString;

	@Before
	public void testPeerString() {
		peerString = new Peer(address);
	}

	@Before
	public void testPeerPeerAddressString() {
		peerPeerAddressString = new Peer(new PeerAddress(address), name);
	}

	@Test
	public void testSetLocation() {
		peerString.setLocation(location2);
		peerPeerAddressString.setLocation(location2);
		assertEquals(location2, peerString.getLocation());
		assertEquals(location2, peerPeerAddressString.getLocation());
		try {
			Field field = GeoVelocity.class.getDeclaredField("currentLocation");
			field.setAccessible(true);
			assertEquals(location2, field.get(peerString.getVelocity()));
			assertEquals(location2, field.get(peerPeerAddressString.getVelocity()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetVelocity() {
		try {
			Field field = GeoVelocity.class.getDeclaredField("currentLocation");
			field.setAccessible(true);
			assertNull(field.get(peerString.getVelocity()));
			assertNull(field.get(peerPeerAddressString.getVelocity()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetLocation() {
		assertNull(peerString.getLocation());
		assertNull(peerPeerAddressString.getLocation());
	}

	@Test
	public void testGetName() {
		assertEquals(nameDefault, peerString.getName());
		assertEquals(name, peerPeerAddressString.getName());
	}

	@Test
	public void testSetName() {
		peerString.setName(name2);
		peerPeerAddressString.setName(name2);
		assertEquals(name2, peerString.getName());
		assertEquals(name2, peerPeerAddressString.getName());
	}

	@Test
	public void testGetAddress() {
		assertEquals(address, peerString.getAddress().toString());
		assertEquals(address, peerPeerAddressString.getAddress().toString());
	}

	@Test
	public void testSetAddress() {
		peerString.setAddress(new PeerAddress(address2));
		peerPeerAddressString.setAddress(new PeerAddress(address2));
		assertEquals(address2, peerString.getAddress().toString());
		assertEquals(address2, peerPeerAddressString.getAddress().toString());
	}

}
