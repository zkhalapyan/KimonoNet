package kimononet.test;

import static org.junit.Assert.*;
import kimononet.geo.GeoLocation;
import kimononet.net.parcel.Parcel;

import org.junit.Before;
import org.junit.Test;

public class GeoLocationTest {

	private final double longitude1 = 12.34567890d;
	private final double latitude1 = 23.45678901d;
	private final float accuracy1 = 34.56789012f;
	private final int timestamp1 = 1234567890;
	private Parcel parcel1 = new Parcel(24);
	private final byte[] array1 = {	(byte)0x40, (byte)0x28, (byte)0xB0, (byte)0xFC, (byte)0xD3, (byte)0x24, (byte)0xD5, (byte)0xA2,
									(byte)0x40, (byte)0x37, (byte)0x74, (byte)0xF0, (byte)0x1F, (byte)0xE3, (byte)0x1F, (byte)0x4A,
									(byte)0x42, (byte)0x0A, (byte)0x45, (byte)0x85,
									(byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2 };

	private final double longitude2 = 45.67890123d;
	private final double latitude2 = 56.78901234d;
	private final float accuracy2 = 67.89012345f;
	private final int timestamp2 = 1234567891;
	private Parcel parcel2 = new Parcel(24);
	private final byte[] array2 = {	(byte)0x40, (byte)0x46, (byte)0xD6, (byte)0xE6, (byte)0x3C, (byte)0x4A, (byte)0x08, (byte)0x37,
									(byte)0x40, (byte)0x4C, (byte)0x64, (byte)0xFE, (byte)0x5B, (byte)0x3A, (byte)0x38, (byte)0x60,
									(byte)0x42, (byte)0x87, (byte)0xC7, (byte)0xBE,
									(byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD3 };

	private final double longitude3 = 78.90123456d;
	private final double latitude3 = 89.01234567d;
	private final float accuracy3 = 90.12345678f;
	private final int timestamp3 = 1234567892;
	private Parcel parcel3 = new Parcel(24);
	private final byte[] array3 = {	(byte)0x40, (byte)0x53, (byte)0xB9, (byte)0xAD, (byte)0xD3, (byte)0xB8, (byte)0x4E, (byte)0x66,
									(byte)0x40, (byte)0x56, (byte)0x40, (byte)0xCA, (byte)0x45, (byte)0x7E, (byte)0x39, (byte)0x6C,
									(byte)0x42, (byte)0xB4, (byte)0x3F, (byte)0x36,
									(byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD4 };

	private GeoLocation locationDoubleDoubleFloat;
	private GeoLocation locationDoubleDoubleFloatInt;
	private GeoLocation locationParcel;

	public GeoLocationTest() {
		testGeoLocationDoubleDoubleFloat();
		testGeoLocationDoubleDoubleFloatInt();
		testGeoLocationParcel();
	}

	@Before
	public void testGeoLocationDoubleDoubleFloat() {
		locationDoubleDoubleFloat = new GeoLocation(longitude1, latitude1, accuracy1);
		locationDoubleDoubleFloat.setTimestamp(timestamp1);
	}

	@Before
	public void testGeoLocationDoubleDoubleFloatInt() {
		locationDoubleDoubleFloatInt = new GeoLocation(longitude2, latitude2, accuracy2, timestamp2);
	}

	@Before
	public void testGeoLocationParcel() {
		parcel1.rewind();
		parcel2.rewind();
		parcel3.rewind();

		parcel1.add(longitude1);
		parcel1.add(latitude1);
		parcel1.add(accuracy1);
		parcel1.add(timestamp1);

		parcel2.add(longitude2);
		parcel2.add(latitude2);
		parcel2.add(accuracy2);
		parcel2.add(timestamp2);

		parcel3.add(longitude3);
		parcel3.add(latitude3);
		parcel3.add(accuracy3);
		parcel3.add(timestamp3);

		parcel1.rewind();
		parcel2.rewind();
		parcel3.rewind();

		locationParcel = new GeoLocation(parcel3);
	}

	@Test
	public void testGetAccuracy() {
		assertEquals(accuracy1, locationDoubleDoubleFloat.getAccuracy(), 0);
		assertEquals(accuracy2, locationDoubleDoubleFloatInt.getAccuracy(), 0);
		assertEquals(accuracy3, locationParcel.getAccuracy(), 0);
	}

	@Test
	public void testGetLatitude() {
		assertEquals(latitude1, locationDoubleDoubleFloat.getLatitude(), 0);
		assertEquals(latitude2, locationDoubleDoubleFloatInt.getLatitude(), 0);
		assertEquals(latitude3, locationParcel.getLatitude(), 0);
	}

	@Test
	public void testGetLongitude() {
		assertEquals(longitude1, locationDoubleDoubleFloat.getLongitude(), 0);
		assertEquals(longitude2, locationDoubleDoubleFloatInt.getLongitude(), 0);
		assertEquals(longitude3, locationParcel.getLongitude(), 0);
	}

	@Test
	public void testGetParcelSize() {
		assertEquals(24, locationDoubleDoubleFloat.getParcelSize());
		assertEquals(24, locationDoubleDoubleFloatInt.getParcelSize());
		assertEquals(24, locationParcel.getParcelSize());
	}

	@Test
	public void testGetTimestamp() {
		assertEquals(timestamp1, locationDoubleDoubleFloat.getTimestamp());
		assertEquals(timestamp2, locationDoubleDoubleFloatInt.getTimestamp());
		assertEquals(timestamp3, locationParcel.getTimestamp());
	}

	@Test
	public void testParse() {
		parcel2.rewind();
		parcel3.rewind();
		parcel1.rewind();

		locationDoubleDoubleFloat.parse(parcel2);
		locationDoubleDoubleFloatInt.parse(parcel3);
		locationParcel.parse(parcel1);

		assertArrayEquals(array2, locationDoubleDoubleFloat.toParcel().toByteArray());
		assertArrayEquals(array3, locationDoubleDoubleFloatInt.toParcel().toByteArray());
		assertArrayEquals(array1, locationParcel.toParcel().toByteArray());
	}

	@Test
	public void testSetLocationDoubleDoubleFloat() {
		locationDoubleDoubleFloat.setLocation(longitude2, latitude2, accuracy2);
		locationDoubleDoubleFloatInt.setLocation(longitude3, latitude3, accuracy3);
		locationParcel.setLocation(longitude1, latitude1, accuracy1);

		// This version of setLocation does not update timestamp, so must do it manually.
		locationDoubleDoubleFloat.setTimestamp(timestamp2);
		locationDoubleDoubleFloatInt.setTimestamp(timestamp3);
		locationParcel.setTimestamp(timestamp1);

		assertArrayEquals(array2, locationDoubleDoubleFloat.toParcel().toByteArray());
		assertArrayEquals(array3, locationDoubleDoubleFloatInt.toParcel().toByteArray());
		assertArrayEquals(array1, locationParcel.toParcel().toByteArray());
	}

	@Test
	public void testSetLocationParcel() {
		parcel2.rewind();
		parcel3.rewind();
		parcel1.rewind();

		locationDoubleDoubleFloat.setLocation(parcel2);
		locationDoubleDoubleFloatInt.setLocation(parcel3);
		locationParcel.setLocation(parcel1);

		assertArrayEquals(array2, locationDoubleDoubleFloat.toParcel().toByteArray());
		assertArrayEquals(array3, locationDoubleDoubleFloatInt.toParcel().toByteArray());
		assertArrayEquals(array1, locationParcel.toParcel().toByteArray());
	}

	@Test
	public void testSetTimestamp() {
		locationDoubleDoubleFloat.setTimestamp(timestamp2);
		locationDoubleDoubleFloatInt.setTimestamp(timestamp3);
		locationParcel.setTimestamp(timestamp1);

		assertEquals(timestamp2, locationDoubleDoubleFloat.getTimestamp());
		assertEquals(timestamp3, locationDoubleDoubleFloatInt.getTimestamp());
		assertEquals(timestamp1, locationParcel.getTimestamp());
	}

	@Test
	public void testToParcel() {
		assertArrayEquals(array1, locationDoubleDoubleFloat.toParcel().toByteArray());
		assertArrayEquals(array2, locationDoubleDoubleFloatInt.toParcel().toByteArray());
		assertArrayEquals(array3, locationParcel.toParcel().toByteArray());
	}

	@Test
	public void testToString() {
		locationDoubleDoubleFloat.toString();
		locationDoubleDoubleFloatInt.toString();
		locationParcel.toString();
	}

}
