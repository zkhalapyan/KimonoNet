package kimononet.test;

import static org.junit.Assert.*;
import kimononet.net.parcel.Parcel;
import kimononet.peer.PeerAddress;

import org.junit.Before;
import org.junit.Test;

public class PeerAddressTest {

	private final byte[] addressByteArray1  = {(byte)0x29, (byte)0x99, (byte)0x35, (byte)0xC1, (byte)0x8E, (byte)0xEB};
	private Parcel addressParcel1 = new Parcel(8);
	private final String addressString1 = "29:99:35:C1:8E:EB";

	private final byte[] addressByteArray2 = {(byte)0x8C, (byte)0x7B, (byte)0x1C, (byte)0x94, (byte)0x3A, (byte)0x1C};
	private Parcel addressParcel2 = new Parcel(8);
	private final String addressString2 = "8C:7B:1C:94:3A:1C";

	private final byte[] addressByteArray3 = {(byte)0xF8, (byte)0xE5, (byte)0xBD, (byte)0x49, (byte)0x9F, (byte)0xF1};
	private Parcel addressParcel3 = new Parcel(8);
	private final String addressString3 = "F8:E5:BD:49:9F:F1";

	private final byte[] addressByteArray4 = {(byte)0xEC, (byte)0xAA, (byte)0x15, (byte)0x90, (byte)0x41, (byte)0xAF};
	private Parcel addressParcel4 = new Parcel(8);
	private final String addressString4 = "EC:AA:15:90:41:AF";

	private PeerAddress peerAddressByteArray;
	private PeerAddress peerAddressParcel;
	private PeerAddress peerAddressString;

	public PeerAddressTest() {
		testPeerAddressByteArray();
		testPeerAddressParcel();
		testPeerAddressString();
	}

	@Before
	public void testPeerAddressByteArray() {
		peerAddressByteArray = new PeerAddress(addressByteArray1);
	}

	@Before
	public void testPeerAddressParcel() {
		addressParcel1.rewind();
		addressParcel2.rewind();
		addressParcel3.rewind();
		addressParcel4.rewind();

		addressParcel1.add(addressByteArray1);
		addressParcel2.add(addressByteArray2);
		addressParcel3.add(addressByteArray3);
		addressParcel4.add(addressByteArray4);

		addressParcel1.rewind();
		addressParcel2.rewind();
		addressParcel3.rewind();
		addressParcel4.rewind();

		peerAddressParcel = new PeerAddress(addressParcel2);
	}

	@Before
	public void testPeerAddressString() {
		peerAddressString = new PeerAddress(addressString3);
	}

	@Test
	public void testEquals() {
		assertTrue((new PeerAddress(addressByteArray4)).equals(new PeerAddress(addressByteArray4)));
		addressParcel4.rewind();
		assertTrue((new PeerAddress(addressByteArray4)).equals(new PeerAddress(addressParcel4)));
		assertTrue((new PeerAddress(addressByteArray4)).equals(new PeerAddress(addressString4)));
		addressParcel4.rewind();
		PeerAddress pa = new PeerAddress(addressParcel4);
		addressParcel4.rewind();
		assertTrue(pa.equals(new PeerAddress(addressParcel4)));
		addressParcel4.rewind();
		assertTrue((new PeerAddress(addressParcel4)).equals(new PeerAddress(addressString4)));
		assertTrue((new PeerAddress(addressString4)).equals(new PeerAddress(addressString4)));
	}

	@Test
	public void testGetParcelSize() {
		assertEquals(8, peerAddressByteArray.getParcelSize());
		assertEquals(8, peerAddressParcel.getParcelSize());
		assertEquals(8, peerAddressString.getParcelSize());
	}

	@Test
	public void testHashCode() {
		peerAddressByteArray.hashCode();
		peerAddressParcel.hashCode();
		peerAddressString.hashCode();
	}

	@Test
	public void testSetAddress() {
		peerAddressByteArray.setAddress(addressString4);
		peerAddressParcel.setAddress(addressString4);
		peerAddressString.setAddress(addressString4);

		assertEquals(addressString4, peerAddressByteArray.toString());
		assertEquals(addressString4, peerAddressParcel.toString());
		assertEquals(addressString4, peerAddressString.toString());

		addressParcel1.rewind();
		addressParcel2.rewind();
		addressParcel3.rewind();
		addressParcel4.rewind();

		assertArrayEquals(addressParcel4.toByteArray(), peerAddressByteArray.toParcel().toByteArray());
		assertArrayEquals(addressParcel4.toByteArray(), peerAddressParcel.toParcel().toByteArray());
		assertArrayEquals(addressParcel4.toByteArray(), peerAddressString.toParcel().toByteArray());
	}

	@Test
	public void testToParcel() {
		assertArrayEquals(addressParcel1.toByteArray(), peerAddressByteArray.toParcel().toByteArray());
		assertArrayEquals(addressParcel2.toByteArray(), peerAddressParcel.toParcel().toByteArray());
		assertArrayEquals(addressParcel3.toByteArray(), peerAddressString.toParcel().toByteArray());
	}

	@Test
	public void testToString() {
		assertEquals(addressString1, peerAddressByteArray.toString());
		assertEquals(addressString2, peerAddressParcel.toString());
		assertEquals(addressString3, peerAddressString.toString());
	}

}
