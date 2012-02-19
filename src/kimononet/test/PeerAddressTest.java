package kimononet.test;

import static org.junit.Assert.*;

import kimononet.peer.address.PeerAddress;

import org.junit.Before;
import org.junit.Test;

public class PeerAddressTest {

	private final String addressString = "01:23:45:67:89:AB";
	private final byte[] addressByteArray  = {(byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67, (byte)0x89, (byte)0xAB};

	private final String addressString2 = "AB:CD:EF:01:23:45";
	private final byte[] addressByteArray2 = {(byte)0xAB, (byte)0xCD, (byte)0xEF, (byte)0x01, (byte)0x23, (byte)0x45};

	private PeerAddress addressFromString;
	private PeerAddress addressFromByteArray;

	@Before
	public void test_PeerAddress_String() {
		addressFromString = new PeerAddress(addressString);
	}

	@Before
	public void test_PeerAddress_ByteArray() {
		addressFromByteArray = new PeerAddress(addressByteArray);
	}

	private void test_toString(String addressStringExpected) {
		assertEquals(addressStringExpected, addressFromString.toString());
		assertEquals(addressStringExpected, addressFromByteArray.toString());
	}

	@Test
	public void test_toString() {
		test_toString(addressString);
	}

	private void test_toByteArray(byte[] addressByteArrayExpected) {
		assertArrayEquals(addressByteArrayExpected, addressFromString.toByteArray());
		assertArrayEquals(addressByteArrayExpected, addressFromByteArray.toByteArray());
	}

	@Test
	public void test_toByteArray() {
		test_toByteArray(addressByteArray);
	}

	@Test
	public void test_setAddress() {
		addressFromString.setAddress(addressString2);
		addressFromByteArray.setAddress(addressString2);
		test_toString(addressString2);
		test_toByteArray(addressByteArray2);
	}

}
