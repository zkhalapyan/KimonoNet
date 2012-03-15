package kimononet.test;

import static org.junit.Assert.*;

import kimononet.geo.GeoLocation;
import kimononet.geo.GeoVelocity;
import kimononet.net.parcel.Parcel;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;

import org.junit.Before;
import org.junit.Test;

public class PeerTest {

	private final GeoLocation location1 = new GeoLocation(12.34567890d, 23.45678901d, 34.56789012f, 1234567890);	// Peer initially at location1.
	private final GeoLocation location2 = new GeoLocation(45.67890123d, 56.78901234d, 67.89012345f, 1234567891);	// Not used initially, since peer initially has no location.
	private final GeoLocation location3 = new GeoLocation(78.90123456d, 89.01234567d, 90.12345678f, 1234567892);	// Peer initially at location3.
	private final GeoLocation location4 = new GeoLocation(-12.34567890d, -23.45678901d, -34.56789012f, 1234567893);	// Not used initially, since peer initially has no location.
	private final GeoLocation location5 = new GeoLocation(-45.67890123d, -56.78901234d, -67.89012345f, 1234567894);	// Not used initially, since peer initially has no location.

	//private GeoVelocity velocity1 = new GeoVelocity(location5);	// Peer initially at location1, but as if arrived from location5. That way, it has a velocity.
	//private GeoVelocity velocity2 = new GeoVelocity(location2);	// Not used initially, since Peer initially has no velocity.
	//private GeoVelocity velocity3 = new GeoVelocity(location2);	// Peer initially at location3, but as if arrived from location2. That way, it has a velocity.
	//private GeoVelocity velocity4 = new GeoVelocity(location4);	// Not used initially, since Peer initially has no velocity.
	//private GeoVelocity velocity5 = new GeoVelocity(location5);	// Not used initially, since Peer initially has no velocity.

	private GeoVelocity velocity1 = new GeoVelocity(12.34567890f, 23.45678901f);
	private GeoVelocity velocity2 = new GeoVelocity(34.56789012f, 45.67890123f);
	private GeoVelocity velocity3 = new GeoVelocity(56.78901234f, 67.89012345f);
	private GeoVelocity velocity4 = new GeoVelocity(78.90123456f, 89.01234567f);
	private GeoVelocity velocity5 = new GeoVelocity(90.12345678f, -12.34567890f);

	private final String name1 = "UAV-001";
	private final String address1 = "29:99:35:C1:8E:EB";
	private final PeerAddress peerAddress1 = new PeerAddress(address1);
	private final Parcel parcel1 = new Parcel(40);

	private final String name2 = "UAV-002";
	private final String address2 = "8C:7B:1C:94:3A:1C";
	private final PeerAddress peerAddress2 = new PeerAddress(address2);
	private final Parcel parcel2 = new Parcel(40);

	private final String name3 = "UAV-003";
	private final String address3 = "F8:E5:BD:49:9F:F1";
	private final PeerAddress peerAddress3 = new PeerAddress(address3);
	private final Parcel parcel3 = new Parcel(40);

	private final String name4 = "UAV-004";
	private final String address4 = "EC:AA:15:90:41:AF";
	private final PeerAddress peerAddress4 = new PeerAddress(address4);
	private final Parcel parcel4 = new Parcel(40);

	private final String name5 = "UAV-005";
	private final String address5 = "CD:28:1A:77:71:2A";
	private final PeerAddress peerAddress5 = new PeerAddress(address5);
	private final Parcel parcel5 = new Parcel(40);

	private Peer peerParcel;
	private Peer peerPeerAddress;
	private Peer peerPeerAddressGeoLocationGeoVelocity;
	private Peer peerPeerAddressString;
	private Peer peerString;

	public PeerTest() {
		testPeerParcel();
		testPeerPeerAddress();
		testPeerPeerAddressGeoLocationGeoVelocity();
		testPeerPeerAddressString();
		testPeerString();
	}

	@Before
	public void testPeerParcel() {
		// Peer initially at location1, but as if arrived from location5. That way, it has a velocity.
		//velocity1.update(location1);

		parcel1.rewind();

		parcel1.add(peerAddress1);
		parcel1.add(location1);
		parcel1.add(velocity1);

		parcel1.rewind();

		peerParcel = new Peer(parcel1);
	}

	@Before
	public void testPeerPeerAddress() {
		parcel2.rewind();

		parcel2.add(peerAddress2);
		parcel2.add(location2);
		parcel2.add(velocity2);

		parcel2.rewind();

		peerPeerAddress = new Peer(peerAddress2);
	}

	@Before
	public void testPeerPeerAddressGeoLocationGeoVelocity() {
		// Peer initially at location3, but as if arrived from location2. That way, it has a velocity.
		//velocity3.update(location3);

		parcel3.rewind();

		parcel3.add(peerAddress3);
		parcel3.add(location3);
		parcel3.add(velocity3);

		parcel3.rewind();

		peerPeerAddressGeoLocationGeoVelocity = new Peer(peerAddress3, location3, velocity3);
	}

	@Before
	public void testPeerPeerAddressString() {
		parcel4.rewind();

		parcel4.add(peerAddress4);
		parcel4.add(location4);
		parcel4.add(velocity4);

		parcel4.rewind();

		peerPeerAddressString = new Peer(peerAddress4, name4);
	}

	@Before
	public void testPeerString() {
		parcel5.rewind();

		parcel5.add(peerAddress5);
		parcel5.add(location5);
		parcel5.add(velocity5);

		parcel5.rewind();

		peerString = new Peer(address5);
	}

	@Test
	public void testGetAddress() {
		assertArrayEquals(peerAddress1.toParcel().toByteArray(), peerParcel.getAddress().toParcel().toByteArray());
		assertArrayEquals(peerAddress2.toParcel().toByteArray(), peerPeerAddress.getAddress().toParcel().toByteArray());
		assertArrayEquals(peerAddress3.toParcel().toByteArray(), peerPeerAddressGeoLocationGeoVelocity.getAddress().toParcel().toByteArray());
		assertArrayEquals(peerAddress4.toParcel().toByteArray(), peerPeerAddressString.getAddress().toParcel().toByteArray());
		assertArrayEquals(peerAddress5.toParcel().toByteArray(), peerString.getAddress().toParcel().toByteArray());
	}

	@Test
	public void testGetLocation() {
		assertArrayEquals(location1.toParcel().toByteArray(), peerParcel.getLocation().toParcel().toByteArray());
		assertNull(peerPeerAddress.getLocation());
		assertArrayEquals(location3.toParcel().toByteArray(), peerPeerAddressGeoLocationGeoVelocity.getLocation().toParcel().toByteArray());
		assertNull(peerPeerAddressString.getLocation());
		assertNull(peerString.getLocation());
	}

	@Test
	public void testGetName() {
		assertEquals("unnamed-peer", peerParcel.getName());
		assertEquals("unnamed-peer", peerPeerAddress.getName());
		assertEquals("unnamed-peer", peerPeerAddressGeoLocationGeoVelocity.getName());
		assertEquals(name4, peerPeerAddressString.getName());
		assertEquals("unnamed-peer", peerString.getName());
	}

	@Test
	public void testGetParcelSize() {
		assertEquals(40, peerParcel.getParcelSize());
		assertEquals(40, peerPeerAddress.getParcelSize());
		assertEquals(40, peerPeerAddressGeoLocationGeoVelocity.getParcelSize());
		assertEquals(40, peerPeerAddressString.getParcelSize());
		assertEquals(40, peerString.getParcelSize());
	}

	@Test
	public void testGetVelocity() {
		assertArrayEquals(velocity1.toParcel().toByteArray(), peerParcel.getVelocity().toParcel().toByteArray());
		assertNotNull(peerPeerAddress.getVelocity());
		assertArrayEquals(velocity3.toParcel().toByteArray(), peerPeerAddressGeoLocationGeoVelocity.getVelocity().toParcel().toByteArray());
		assertNotNull(peerPeerAddressString.getVelocity());
		assertNotNull(peerString.getVelocity());
	}

	@Test
	public void testParse() {
		parcel2.rewind();
		parcel3.rewind();
		parcel4.rewind();
		parcel5.rewind();
		parcel1.rewind();

		peerParcel.parse(parcel2);
		peerPeerAddress.parse(parcel3);
		peerPeerAddressGeoLocationGeoVelocity.parse(parcel4);
		peerPeerAddressString.parse(parcel5);
		peerString.parse(parcel1);

		assertArrayEquals(parcel2.toByteArray(), peerParcel.toParcel().toByteArray());
		assertArrayEquals(parcel3.toByteArray(), peerPeerAddress.toParcel().toByteArray());
		assertArrayEquals(parcel4.toByteArray(), peerPeerAddressGeoLocationGeoVelocity.toParcel().toByteArray());
		assertArrayEquals(parcel5.toByteArray(), peerPeerAddressString.toParcel().toByteArray());
		assertArrayEquals(parcel1.toByteArray(), peerString.toParcel().toByteArray());
	}

	@Test
	public void testSetAddress() {
		peerParcel.setAddress(peerAddress2);
		peerPeerAddress.setAddress(peerAddress3);
		peerPeerAddressGeoLocationGeoVelocity.setAddress(peerAddress4);
		peerPeerAddressString.setAddress(peerAddress5);
		peerString.setAddress(peerAddress1);

		assertArrayEquals(peerAddress2.toParcel().toByteArray(), peerParcel.getAddress().toParcel().toByteArray());
		assertArrayEquals(peerAddress3.toParcel().toByteArray(), peerPeerAddress.getAddress().toParcel().toByteArray());
		assertArrayEquals(peerAddress4.toParcel().toByteArray(), peerPeerAddressGeoLocationGeoVelocity.getAddress().toParcel().toByteArray());
		assertArrayEquals(peerAddress5.toParcel().toByteArray(), peerPeerAddressString.getAddress().toParcel().toByteArray());
		assertArrayEquals(peerAddress1.toParcel().toByteArray(), peerString.getAddress().toParcel().toByteArray());
	}

	@Test
	public void testSetLocation() {
		// These peers were constructed with locations in parentheses:
		// Peer 1: location5 -> (location1) -> location2.
		// Peer 3: location2 -> (location3) -> location4.

		/* 1 */ peerParcel.setLocation(location2);
		/* 3 */ peerPeerAddressGeoLocationGeoVelocity.setLocation(location4);

		/* 1 */ assertArrayEquals(location2.toParcel().toByteArray(), peerParcel.getLocation().toParcel().toByteArray());
		/* 3 */ assertArrayEquals(location4.toParcel().toByteArray(), peerPeerAddressGeoLocationGeoVelocity.getLocation().toParcel().toByteArray());

		///* 1 */ velocity1.update(location2);
		///* 3 */ velocity3.update(location4);

		///* 1 */ assertArrayEquals(velocity1.toParcel().toByteArray(), peerParcel.getVelocity().toParcel().toByteArray());
		///* 3 */ assertArrayEquals(velocity3.toParcel().toByteArray(), peerPeerAddressGeoLocationGeoVelocity.getVelocity().toParcel().toByteArray());

		// These peers were constructed without locations.

		peerPeerAddress.setLocation(location2);			// First time setting location. Peer 2 is now at location2.
		peerPeerAddressString.setLocation(location4);	// First time setting location. Peer 4 is now at location4.
		peerString.setLocation(location5);				// First time setting location. Peer 5 is now at location5.

		assertArrayEquals(location2.toParcel().toByteArray(), peerPeerAddress.getLocation().toParcel().toByteArray());
		assertArrayEquals(location4.toParcel().toByteArray(), peerPeerAddressString.getLocation().toParcel().toByteArray());
		assertArrayEquals(location5.toParcel().toByteArray(), peerString.getLocation().toParcel().toByteArray());

		//velocity2.update(location2);	// Already set location using constructor.
		//velocity4.update(location4);	// Already set location using constructor.
		//velocity5.update(location5);	// Already set location using constructor.

		//assertArrayEquals(velocity2.toParcel().toByteArray(), peerPeerAddress.getVelocity().toParcel().toByteArray());
		//assertArrayEquals(velocity4.toParcel().toByteArray(), peerPeerAddressString.getVelocity().toParcel().toByteArray());
		//assertArrayEquals(velocity5.toParcel().toByteArray(), peerString.getVelocity().toParcel().toByteArray());

		peerPeerAddress.setLocation(location3);			// Second time setting location. Peer 2 went from location2 -> location3.
		peerPeerAddressString.setLocation(location5);	// Second time setting location. Peer 4 went from location4 -> location5.
		peerString.setLocation(location1);				// Second time setting location. Peer 5 went from location5 -> location1.

		assertArrayEquals(location3.toParcel().toByteArray(), peerPeerAddress.getLocation().toParcel().toByteArray());
		assertArrayEquals(location5.toParcel().toByteArray(), peerPeerAddressString.getLocation().toParcel().toByteArray());
		assertArrayEquals(location1.toParcel().toByteArray(), peerString.getLocation().toParcel().toByteArray());

		//velocity2.update(location3);
		//velocity4.update(location5);
		//velocity5.update(location1);

		//assertArrayEquals(velocity2.toParcel().toByteArray(), peerPeerAddress.getVelocity().toParcel().toByteArray());
		//assertArrayEquals(velocity4.toParcel().toByteArray(), peerPeerAddressString.getVelocity().toParcel().toByteArray());
		//assertArrayEquals(velocity5.toParcel().toByteArray(), peerString.getVelocity().toParcel().toByteArray());
	}

	@Test
	public void testSetName() {
		peerParcel.setName(name2);
		peerPeerAddress.setName(name3);
		peerPeerAddressGeoLocationGeoVelocity.setName(name4);
		peerPeerAddressString.setName(name5);
		peerString.setName(name1);

		assertEquals(name2, peerParcel.getName());
		assertEquals(name3, peerPeerAddress.getName());
		assertEquals(name4, peerPeerAddressGeoLocationGeoVelocity.getName());
		assertEquals(name5, peerPeerAddressString.getName());
		assertEquals(name1, peerString.getName());
	}

	@Test
	public void testSetVelocity() {
		peerParcel.setVelocity(velocity2);
		peerPeerAddress.setVelocity(velocity3);
		peerPeerAddressGeoLocationGeoVelocity.setVelocity(velocity4);
		peerPeerAddressString.setVelocity(velocity5);
		peerString.setVelocity(velocity1);

		assertArrayEquals(velocity2.toParcel().toByteArray(), peerParcel.getVelocity().toParcel().toByteArray());
		assertArrayEquals(velocity3.toParcel().toByteArray(), peerPeerAddress.getVelocity().toParcel().toByteArray());
		assertArrayEquals(velocity4.toParcel().toByteArray(), peerPeerAddressGeoLocationGeoVelocity.getVelocity().toParcel().toByteArray());
		assertArrayEquals(velocity5.toParcel().toByteArray(), peerPeerAddressString.getVelocity().toParcel().toByteArray());
		assertArrayEquals(velocity1.toParcel().toByteArray(), peerString.getVelocity().toParcel().toByteArray());
	}

	@Test
	public void testToParcel() {
		assertArrayEquals(parcel1.toParcel().toByteArray(), peerParcel.toParcel().toByteArray());
		//assertArrayEquals(parcel2.toParcel().toByteArray(), peerPeerAddress.toParcel().toByteArray());
		assertArrayEquals(parcel3.toParcel().toByteArray(), peerPeerAddressGeoLocationGeoVelocity.toParcel().toByteArray());
		//assertArrayEquals(parcel4.toParcel().toByteArray(), peerPeerAddressString.toParcel().toByteArray());
		//assertArrayEquals(parcel5.toParcel().toByteArray(), peerString.toParcel().toByteArray());
	}

}
