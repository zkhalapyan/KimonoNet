package kimononet.test;

import static org.junit.Assert.*;
import kimononet.geo.GeoLocation;

import org.junit.Before;
import org.junit.Test;

public class GeoLocationTest {

	private final long timestamp = 1234567890;
	private final double longitude = -77.180555;
	private final double latitude = 39.105480;
	private final double accuracy = 123.4567890;
	private final byte[] byteArray = {	(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2,		// timestamp from above (big-endian)
										(byte)0xC0, (byte)0x53, (byte)0x4B, (byte)0x8E, (byte)0x36, (byte)0x8F, (byte)0x08, (byte)0x46,		// longitude from above (big-endian)
										(byte)0x40, (byte)0x43, (byte)0x8D, (byte)0x80, (byte)0x5E, (byte)0x5F, (byte)0x30, (byte)0xE8,		// latitude from above (big-endian)
										(byte)0x40, (byte)0x5E, (byte)0xDD, (byte)0x3C, (byte)0x07, (byte)0xEE, (byte)0x0B, (byte)0x0B};	// accuracy from above (big-endian)

	private final long timestamp2 = 1111111111;
	private final double longitude2 = -117.767195;
	private final double latitude2 = 33.65854;
	private final double accuracy2 = 456.7890123;
	private final byte[] byteArray2 = {	(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x42, (byte)0x3A, (byte)0x35, (byte)0xC7,		// timestamp2 from above (big-endian)
										(byte)0xC0, (byte)0x5D, (byte)0x71, (byte)0x19, (byte)0xB9, (byte)0x0E, (byte)0xA9, (byte)0xE7,		// longitude2 from above (big-endian)
										(byte)0x40, (byte)0x40, (byte)0xD4, (byte)0x4B, (byte)0x09, (byte)0xE9, (byte)0x8D, (byte)0xCE,		// latitude2 from above (big-endian)
										(byte)0x40, (byte)0x7C, (byte)0x8C, (byte)0x9F, (byte)0xCB, (byte)0x5C, (byte)0x8A, (byte)0x45};	// accuracy2 from above (big-endian)

	private GeoLocation locationFromDoubles;
	private GeoLocation locationFromByteArray;

	@Before
	public void test_GeoLocation_Doubles() {
		locationFromDoubles = new GeoLocation(longitude, latitude, accuracy);
	}

	@Before
	public void test_GeoLocation_ByteArray() {
		locationFromByteArray = new GeoLocation(byteArray);
	}

	private void test_getLongitude(double longitudeExpected) {
		assertEquals(longitudeExpected, locationFromDoubles.getLongitude(), 0);
		assertEquals(longitudeExpected, locationFromByteArray.getLongitude(), 0);
	}

	@Test
	public void test_getLongitude() {
		test_getLongitude(longitude);
	}

	private void test_getLatitude(double latitudeExpected) {
		assertEquals(latitudeExpected, locationFromDoubles.getLatitude(), 0);
		assertEquals(latitudeExpected, locationFromByteArray.getLatitude(), 0);
	}

	@Test
	public void test_getLatitude() {
		test_getLatitude(latitude);
	}

	private void test_getAccuracy(double accuracyExpected) {
		assertEquals(accuracyExpected, locationFromDoubles.getAccuracy(), 0);
		assertEquals(accuracyExpected, locationFromByteArray.getAccuracy(), 0);
	}

	@Test
	public void test_getAccuracy() {
		test_getAccuracy(accuracy);
	}

	@Test
	public void test_getLastUpdateTime() {
		// Not checking locationFromDoubles.getLastUpdateTime() because value
		// varies with current system time.

		assertEquals(timestamp, locationFromByteArray.getLastUpdateTime());
	}

	// Use this when the timestamp contained in the byte array varies with current system time.
	private void test_toByteArray_SkipTimestamp(byte[] byteArrayExpected, byte[] byteArrayActual)
	{
		final int offset = 8;
		final int length = 24;

		byte[] byteArrayExpectedNoTimestamp = new byte[length];
		byte[] byteArrayActualNoTimestamp = new byte[length];

		System.arraycopy(byteArrayExpected, offset, byteArrayExpectedNoTimestamp, 0, length);
		System.arraycopy(byteArrayActual, offset, byteArrayActualNoTimestamp, 0, length);

		assertArrayEquals(byteArrayExpectedNoTimestamp, byteArrayActualNoTimestamp);
	}

	@Test
	public void test_toByteArray() {
		test_toByteArray_SkipTimestamp(byteArray, locationFromDoubles.toByteArray());
		assertArrayEquals(byteArray, locationFromByteArray.toByteArray());
	}

	@Test
	public void test_setLocation_Doubles() {
		locationFromDoubles.setLocation(longitude2, latitude2, accuracy2);
		locationFromByteArray.setLocation(longitude2, latitude2, accuracy2);
		test_getLongitude(longitude2);
		test_getLatitude(latitude2);
		test_getAccuracy(accuracy2);
		test_toByteArray_SkipTimestamp(byteArray2, locationFromDoubles.toByteArray());
		test_toByteArray_SkipTimestamp(byteArray2, locationFromByteArray.toByteArray());
	}

	@Test
	public void test_setLocation_ByteArray() {
		locationFromDoubles.setLocation(byteArray2);
		locationFromByteArray.setLocation(byteArray2);
		test_getLongitude(longitude2);
		test_getLatitude(latitude2);
		test_getAccuracy(accuracy2);
		assertEquals(timestamp2, locationFromDoubles.getLastUpdateTime());
		assertEquals(timestamp2, locationFromByteArray.getLastUpdateTime());
		assertArrayEquals(byteArray2, locationFromDoubles.toByteArray());
		assertArrayEquals(byteArray2, locationFromByteArray.toByteArray());
	}

}
