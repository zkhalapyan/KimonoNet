package kimononet.test;

import static org.junit.Assert.*;
import kimononet.geo.GeoLocation;
import kimononet.geo.GeoVelocity;
import kimononet.net.Packet;
import kimononet.net.PacketType;
import kimononet.net.parcel.Parcel;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;

import org.junit.Before;
import org.junit.Test;

public class PacketTest {

	//private final GeoLocation locationPre1 = new GeoLocation(12.34567890d, 23.45678901d, 34.56789012f, 1234567890);
	private final GeoLocation location1 = new GeoLocation(45.67890123d, 56.78901234d, 67.89012345f, 1234567891);
	//private GeoVelocity velocity1 = new GeoVelocity(locationPre1);
	private GeoVelocity velocity1 = new GeoVelocity(12.34567890f, 23.45678901f);
	private final byte version1 = (byte)0x29;
	private final PacketType type1 = PacketType.BEACON;
	//private final Peer peer1 = new Peer(new PeerAddress("99:35:C1:8E:EB:8C"), location1, new GeoVelocity(locationPre1));
	private final Peer peer1 = new Peer(new PeerAddress("99:35:C1:8E:EB:8C"), location1, velocity1);
	private Parcel parcel1 = new Parcel(44);
	private Parcel parcel1withContents = new Parcel(44 + 16);
	private final Parcel contents1 = new Parcel(new byte[] {(byte)0x90, (byte)0x41, (byte)0xAF, (byte)0xCD, (byte)0x28, (byte)0x1A, (byte)0x77, (byte)0x71, (byte)0x2A, (byte)0x35, (byte)0x92, (byte)0x4B, (byte)0xE1, (byte)0xFA, (byte)0x8A, (byte)0x47});

	//private final GeoLocation locationPre2 = new GeoLocation(78.90123456d, 89.01234567d, 90.12345678f, 1234567892);
	private final GeoLocation location2 = new GeoLocation(-12.34567890d, -23.45678901d, -34.56789012f, 1234567893);
	//private GeoVelocity velocity2 = new GeoVelocity(locationPre2);
	private GeoVelocity velocity2 = new GeoVelocity(34.56789012f, 45.67890123f);
	private final byte version2 = (byte)0x7B;
	private final PacketType type2 = PacketType.DATA;
	//private final Peer peer2 = new Peer(new PeerAddress("1C:94:3A:1C:F8:E5"), location2, new GeoVelocity(locationPre2));
	private final Peer peer2 = new Peer(new PeerAddress("1C:94:3A:1C:F8:E5"), location2, velocity2);
	private Parcel parcel2 = new Parcel(44);
	private Parcel parcel2withContents = new Parcel(44 + 32);
	private final Parcel contents2 = new Parcel(new byte[] {(byte)0x8E, (byte)0x27, (byte)0x79, (byte)0x08, (byte)0x99, (byte)0x65, (byte)0xC9, (byte)0x53, (byte)0x43, (byte)0x14, (byte)0x89, (byte)0x23, (byte)0x1F, (byte)0x8B, (byte)0x0C, (byte)0x03, (byte)0x81, (byte)0x63, (byte)0x58, (byte)0xFD, (byte)0xD8, (byte)0x09, (byte)0x55, (byte)0x8B, (byte)0x46, (byte)0x84, (byte)0xCD, (byte)0xF5, (byte)0xBD, (byte)0x04, (byte)0x18, (byte)0x3A});

	//private final GeoLocation locationPre3 = new GeoLocation(-45.67890123d, -56.78901234d, -67.89012345f, 1234567894);
	private final GeoLocation location3 = new GeoLocation(-78.90123456d, -89.01234567d, -90.12345678f, 1234567895);
	//private GeoVelocity velocity3 = new GeoVelocity(locationPre3);
	private GeoVelocity velocity3 = new GeoVelocity(56.78901234f, 67.89012345f);
	private final byte version3 = (byte)0xBD;
	private final PacketType type3 = PacketType.DATA;
	//private final Peer peer3 = new Peer(new PeerAddress("49:9F:F1:EC:AA:15"), location3, new GeoVelocity(locationPre3));
	private final Peer peer3 = new Peer(new PeerAddress("49:9F:F1:EC:AA:15"), location3, velocity3);
	private Parcel parcel3 = new Parcel(44);
	private Parcel parcel3withContents = new Parcel(44 + 64);
	private final Parcel contents3 = new Parcel(new byte[] {(byte)0xA8, (byte)0x92, (byte)0xD4, (byte)0xA5, (byte)0xD8, (byte)0x48, (byte)0x39, (byte)0xED, (byte)0xE3, (byte)0x5A, (byte)0xBE, (byte)0x5A, (byte)0x89, (byte)0x4D, (byte)0x94, (byte)0x2F, (byte)0xD5, (byte)0xB6, (byte)0xFD, (byte)0x9A, (byte)0xA2, (byte)0x2D, (byte)0x91, (byte)0x5A, (byte)0x25, (byte)0x19, (byte)0xB1, (byte)0x17, (byte)0x54, (byte)0x8D, (byte)0xC7, (byte)0x1C, (byte)0x0F, (byte)0x64, (byte)0x01, (byte)0xA7, (byte)0x73, (byte)0xDC, (byte)0x44, (byte)0x8D, (byte)0xDE, (byte)0xAC, (byte)0x14, (byte)0xD9, (byte)0xF1, (byte)0x17, (byte)0xB3, (byte)0xCD, (byte)0x95, (byte)0xD7, (byte)0xD4, (byte)0x2A, (byte)0x2B, (byte)0x1E, (byte)0x8B, (byte)0xB0, (byte)0xC1, (byte)0x95, (byte)0xDB, (byte)0xB3, (byte)0xB6, (byte)0x62, (byte)0xF6, (byte)0x50});

	private Packet packet;
	private Packet packetBytePacketTypePeer;
	private Packet packetParcel;

	public PacketTest() {
		testPacket();
		testPacketBytePacketTypePeer();
		testPacketParcel();
	}

	@Before
	public void testPacket() {
		//velocity1.update(location1);
		//peer1.getVelocity().update(location1);

		parcel1.rewind();

		parcel1.add(new byte[] {(byte)0xBE, (byte)0xC0});
		parcel1.add(version1);
		parcel1.add(type1);
		parcel1.add(peer1);

		parcel1withContents.rewind();

		parcel1withContents.add(new byte[] {(byte)0xBE, (byte)0xC0});
		parcel1withContents.add(version1);
		parcel1withContents.add(type1);
		parcel1withContents.add(peer1);
		parcel1withContents.add(contents1);

		packet = new Packet();
	}

	@Before
	public void testPacketBytePacketTypePeer() {
		//velocity2.update(location2);
		//peer2.getVelocity().update(location2);

		parcel2.rewind();

		parcel2.add(new byte[] {(byte)0xBE, (byte)0xC0});
		parcel2.add(version2);
		parcel2.add(type2);
		parcel2.add(peer2);

		parcel2withContents.rewind();

		parcel2withContents.add(new byte[] {(byte)0xBE, (byte)0xC0});
		parcel2withContents.add(version2);
		parcel2withContents.add(type2);
		parcel2withContents.add(peer2);
		parcel2withContents.add(contents2);

		packetBytePacketTypePeer = new Packet(version2, type2, peer2);
	}

	@Before
	public void testPacketParcel() {
		//velocity3.update(location3);
		//peer3.getVelocity().update(location3);

		parcel3.rewind();

		parcel3.add(new byte[] {(byte)0xBE, (byte)0xC0});
		parcel3.add(version3);
		parcel3.add(type3);
		parcel3.add(peer3);

		parcel3withContents.rewind();

		parcel3withContents.add(new byte[] {(byte)0xBE, (byte)0xC0});
		parcel3withContents.add(version3);
		parcel3withContents.add(type3);
		parcel3withContents.add(peer3);
		parcel3withContents.add(contents3);

		packetParcel = new Packet(parcel3);
	}

	@Test
	public void testGetContents() {
		assertNull(packet.getContents());
		assertNull(packetBytePacketTypePeer.getContents());
		assertNull(packetParcel.getContents());
	}

	@Test
	public void testGetContentsLength() {
		assertEquals(0, packet.getContentsLength());
		assertEquals(0, packetBytePacketTypePeer.getContentsLength());
		assertEquals(0, packetParcel.getContentsLength());
	}

	@Test
	public void testGetParcelSize() {
		assertEquals(44, packet.getParcelSize());
		assertEquals(44, packetBytePacketTypePeer.getParcelSize());
		assertEquals(44, packetParcel.getParcelSize());
	}

	@Test
	public void testGetPeer() {
		assertNull(packet.getPeer());
		assertArrayEquals(peer2.toParcel().toByteArray(), packetBytePacketTypePeer.getPeer().toParcel().toByteArray());
		assertArrayEquals(peer3.toParcel().toByteArray(), packetParcel.getPeer().toParcel().toByteArray());
	}

	@Test
	public void testGetType() {
		assertNull(packet.getType());
		assertEquals(type2, packetBytePacketTypePeer.getType());
		assertEquals(type3, packetParcel.getType());
	}

	@Test
	public void testMagicCheck() {
		assertTrue(packet.magicCheck());
		assertTrue(packetBytePacketTypePeer.magicCheck());
		assertTrue(packetParcel.magicCheck());
	}

	@Test
	public void testParse() {
		packet.parse(parcel2);
		packetBytePacketTypePeer.parse(parcel3);
		packetParcel.parse(parcel1);

		parcel2.rewind();
		parcel3.rewind();
		parcel1.rewind();

		assertArrayEquals(parcel2.toByteArray(), packet.toParcel().toByteArray());
		assertArrayEquals(parcel3.toByteArray(), packetBytePacketTypePeer.toParcel().toByteArray());
		assertArrayEquals(parcel1.toByteArray(), packetParcel.toParcel().toByteArray());

		packet.parse(parcel1withContents);
		packetBytePacketTypePeer.parse(parcel2withContents);
		packetParcel.parse(parcel3withContents);

		parcel1withContents.rewind();
		parcel2withContents.rewind();
		parcel3withContents.rewind();

		assertArrayEquals(parcel1withContents.toByteArray(), packet.toParcel().toByteArray());
		assertArrayEquals(parcel2withContents.toByteArray(), packetBytePacketTypePeer.toParcel().toByteArray());
		assertArrayEquals(parcel3withContents.toByteArray(), packetParcel.toParcel().toByteArray());

		assertArrayEquals(contents1.toParcel().toByteArray(), packet.getContents().toByteArray());
		assertArrayEquals(contents2.toParcel().toByteArray(), packetBytePacketTypePeer.getContents().toByteArray());
		assertArrayEquals(contents3.toParcel().toByteArray(), packetParcel.getContents().toByteArray());

		assertEquals(contents1.toParcel().toByteArray().length, packet.getContentsLength());
		assertEquals(contents2.toParcel().toByteArray().length, packetBytePacketTypePeer.getContentsLength());
		assertEquals(contents3.toParcel().toByteArray().length, packetParcel.getContentsLength());

		assertEquals(44 + 16, packet.getParcelSize());
		assertEquals(44 + 32, packetBytePacketTypePeer.getParcelSize());
		assertEquals(44 + 64, packetParcel.getParcelSize());
	}

	@Test
	public void testSetContents() {
		packet.setContents(contents1);
		packetBytePacketTypePeer.setContents(contents2);
		packetParcel.setContents(contents3);

		assertArrayEquals(contents1.toParcel().toByteArray(), packet.getContents().toByteArray());
		assertArrayEquals(contents2.toParcel().toByteArray(), packetBytePacketTypePeer.getContents().toByteArray());
		assertArrayEquals(contents3.toParcel().toByteArray(), packetParcel.getContents().toByteArray());

		assertEquals(contents1.toParcel().toByteArray().length, packet.getContentsLength());
		assertEquals(contents2.toParcel().toByteArray().length, packetBytePacketTypePeer.getContentsLength());
		assertEquals(contents3.toParcel().toByteArray().length, packetParcel.getContentsLength());

		assertEquals(44 + 16, packet.getParcelSize());
		assertEquals(44 + 32, packetBytePacketTypePeer.getParcelSize());
		assertEquals(44 + 64, packetParcel.getParcelSize());
	}

	@Test
	public void testSetType() {
		packet.setType(PacketType.DATA);
		packetBytePacketTypePeer.setType(PacketType.BEACON);
		packetParcel.setType(PacketType.BEACON);

		assertEquals(PacketType.DATA, packet.getType());
		assertEquals(PacketType.BEACON, packetBytePacketTypePeer.getType());
		assertEquals(PacketType.BEACON, packetParcel.getType());
	}

	@Test
	public void testToParcel() {
		assertArrayEquals(parcel2.toByteArray(), packetBytePacketTypePeer.toParcel().toByteArray());
		assertArrayEquals(parcel3.toByteArray(), packetParcel.toParcel().toByteArray());
	}

	@Test
	public void testToString() {
		packet.toString();
		packetBytePacketTypePeer.toString();
		packetParcel.toString();
	}

}
