package kimononet.test;

import static org.junit.Assert.*;
import kimononet.net.parcel.Parcel;

import org.junit.Before;
import org.junit.Test;

public class ParcelTest {

	private Parcel parcelInt;
	private Parcel parcelByte;
	private Parcel parcelByteArray;

	public ParcelTest() {
		testParcelInt();
		testParcelByte();
		testParcelByteArray();
	}

	@Before
	public void testParcelInt() {
		parcelInt = new Parcel((int)16);
	}

	@Before
	public void testParcelByte() {
		parcelByte = new Parcel((byte)0xFF);
	}

	@Before
	public void testParcelByteArray() {
		parcelByteArray = new Parcel(new byte[] {(byte)0xFE, (byte)0xFD});
	}

	@Test
	public void testAddByte() {
		parcelInt.add((byte)0xFC);
		assertEquals(16, parcelInt.capacity());
		assertEquals(1, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xFC, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((byte)0xFB);
		assertEquals(16, parcelInt.capacity());
		assertEquals(2, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xFC, (byte)0xFB, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddDouble() {
		parcelInt.add((double)-77.180555);
		assertEquals(16, parcelInt.capacity());
		assertEquals(1*8, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xC0, (byte)0x53, (byte)0x4B, (byte)0x8E, (byte)0x36, (byte)0x8F, (byte)0x08, (byte)0x46, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((double)39.105480);
		assertEquals(16, parcelInt.capacity());
		assertEquals(2*8, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xC0, (byte)0x53, (byte)0x4B, (byte)0x8E, (byte)0x36, (byte)0x8F, (byte)0x08, (byte)0x46, (byte)0x40, (byte)0x43, (byte)0x8D, (byte)0x80, (byte)0x5E, (byte)0x5F, (byte)0x30, (byte)0xE8}, parcelInt.toByteArray());
	}

	@Test
	public void testAddInt() {
		parcelInt.add((int)1234567890);
		assertEquals(16, parcelInt.capacity());
		assertEquals(1*4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((int)1111111111);
		assertEquals(16, parcelInt.capacity());
		assertEquals(2*4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2, (byte)0x42, (byte)0x3A, (byte)0x35, (byte)0xC7, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddFloat() {
		parcelInt.add((float)123.4567890);
		assertEquals(16, parcelInt.capacity());
		assertEquals(1*4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x42, (byte)0xF6, (byte)0xE9, (byte)0xE0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((float)456.7890123);
		assertEquals(16, parcelInt.capacity());
		assertEquals(2*4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x42, (byte)0xF6, (byte)0xE9, (byte)0xE0, (byte)0x43, (byte)0xE4, (byte)0x64, (byte)0xFE, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddLong() {
		parcelInt.add((long)1234567890);
		assertEquals(16, parcelInt.capacity());
		assertEquals(1*8, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((long)1111111111);
		assertEquals(16, parcelInt.capacity());
		assertEquals(2*8, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x42, (byte)0x3A, (byte)0x35, (byte)0xC7}, parcelInt.toByteArray());
	}

	@Test
	public void testAddString() {
		parcelInt.add((String)"foo");
		assertEquals(16, parcelInt.capacity());
		assertEquals(3, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)'f', (byte)'o', (byte)'o', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((String)"bar");
		assertEquals(16, parcelInt.capacity());
		assertEquals(6, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)'f', (byte)'o', (byte)'o', (byte)'b', (byte)'a', (byte)'r', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddByteArray() {
		parcelInt.add(new byte[] {(byte)0x00, (byte)0x01, (byte)0x02});
		assertEquals(16, parcelInt.capacity());
		assertEquals(3, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x01, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add(new byte[] {(byte)0x03, (byte)0x04, (byte)0x05});
		assertEquals(16, parcelInt.capacity());
		assertEquals(6, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, (byte)0x05, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddParcelable() {
		parcelInt.add(new Parcel(new byte[] {(byte)0xDE, (byte)0xAD, (byte)0xBE, (byte)0xEF}));
		assertEquals(16, parcelInt.capacity());
		assertEquals(4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xDE, (byte)0xAD, (byte)0xBE, (byte)0xEF, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add(new Parcel(new byte[] {(byte)0x0F, (byte)0xF1, (byte)0xCE, (byte)0x14}));
		assertEquals(16, parcelInt.capacity());
		assertEquals(8, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xDE, (byte)0xAD, (byte)0xBE, (byte)0xEF, (byte)0x0F, (byte)0xF1, (byte)0xCE, (byte)0x14, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testToByteArray() {
		assertArrayEquals(new byte[16], parcelInt.toByteArray());
		assertArrayEquals(new byte[] {(byte)0xFF}, parcelByte.toByteArray());
		assertArrayEquals(new byte[] {(byte)0xFE, (byte)0xFD}, parcelByteArray.toByteArray());
	}

	@Test
	public void testToParcel() {
		assertEquals(parcelInt, parcelInt.toParcel());
		assertEquals(parcelByte, parcelByte.toParcel());
		assertEquals(parcelByteArray, parcelByteArray.toParcel());
	}

	@Test
	public void testSetParcel() {
		parcelInt.setParcel(parcelByteArray);
		assertEquals(2, parcelInt.capacity());
		assertEquals(2, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xFE, (byte)0xFD}, parcelInt.toByteArray());
	}

	@Test
	public void testGetParcelSize() {
		assertEquals(0, parcelInt.getParcelSize());
		assertEquals(1, parcelByte.getParcelSize());
		assertEquals(2, parcelByteArray.getParcelSize());
	}

	@Test
	public void testCapacity() {
		assertEquals(16, parcelInt.capacity());
		assertEquals(1, parcelByte.capacity());
		assertEquals(2, parcelByteArray.capacity());
	}

	@Test
	public void testCombineParcelables() {
		Parcel parcel = Parcel.combineParcelables(new Parcel(new byte[] {(byte)0xDE, (byte)0xAD, (byte)0xBE, (byte)0xEF}), new Parcel(new byte[] {(byte)0x0F, (byte)0xF1, (byte)0xCE, (byte)0x14}));
		assertArrayEquals(new byte[] {(byte)0xDE, (byte)0xAD, (byte)0xBE, (byte)0xEF, (byte)0x0F, (byte)0xF1, (byte)0xCE, (byte)0x14}, parcel.toByteArray());
	}

}
