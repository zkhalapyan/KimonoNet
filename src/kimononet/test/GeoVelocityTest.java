package kimononet.test;

import static org.junit.Assert.*;
import kimononet.geo.GeoLocation;
import kimononet.geo.GeoVelocity;
import kimononet.net.parcel.Parcel;

import org.junit.Before;
import org.junit.Test;

public class GeoVelocityTest {

	// Expected distances and bearings calculated using inverse tool from
	// http://www.ngs.noaa.gov/PC_PROD/Inv_Fwd/ and Android
	// Location.distanceBetween function.
	private final int dTimeLocation1to2 = 12345;
	private final int dTimeLocation2to3 = 23456;
	private final float speedLocation1to2 = (float)((double)3660677.1674 / (double)dTimeLocation1to2);
	private final float speedLocation2to3 = (float)((double)3660677.1674 / (double)dTimeLocation2to3);
	private final float initBearingLocation1to2 = -86.642235f;
	private final float finalBearingLocation1to2 = -111.41504f;
	private final float initBearingLocation2to3 = 68.58496f;
	private final float finalBearingLocation2to3 = 93.357765f;

	private final double longitude1 = -77.180555d;
	private final double latitude1 = 39.105480d;
	private final float accuracy1 = 12.34567890f;
	private final int timestamp1 = 1234567890;
	private GeoLocation location1;

	private final double longitude2 = -117.767195d;
	private final double latitude2 = 33.65854d;
	private final float accuracy2 = 23.45678901f;
	private final int timestamp2 = timestamp1 + dTimeLocation1to2;
	private GeoLocation location2;

	private final double longitude3 = longitude1;
	private final double latitude3 = latitude1;
	private final float accuracy3 = accuracy1;
	private final int timestamp3 = timestamp2 + dTimeLocation2to3;
	private GeoLocation location3;

	private final byte[] parcelArrayLocation1to2 = {(byte)0x43, (byte)0x94, (byte)0x43, (byte)0xFD,
													(byte)0xC2, (byte)0xDE, (byte)0xD4, (byte)0x80};

	private final byte[] parcelArrayLocation2to3 = {(byte)0x43, (byte)0x1C, (byte)0x10, (byte)0xD2,
													(byte)0x42, (byte)0xBA, (byte)0xB7, (byte)0x2D};

	private Parcel parcelLocation1to2 = new Parcel(parcelArrayLocation1to2); 
	private Parcel parcelLocation2to3 = new Parcel(parcelArrayLocation2to3); 

	private GeoVelocity velocity;
	private GeoVelocity velocityGeoLocation;
	private GeoVelocity velocityParcel;

	public GeoVelocityTest() {
		testGeoVelocity();
		testGeoVelocityGeoLocation();
		testGeoVelocityParcel();
	}

	@Before
	public void testGeoVelocity() {
		velocity = new GeoVelocity();
	}

	@Before
	public void testGeoVelocityGeoLocation() {
		location1 = new GeoLocation(longitude1, latitude1, accuracy1, timestamp1);
		location2 = new GeoLocation(longitude2, latitude2, accuracy2, timestamp2);
		location3 = new GeoLocation(longitude3, latitude3, accuracy3, timestamp3);

		velocityGeoLocation = new GeoVelocity(location1);
	}

	@Before
	public void testGeoVelocityParcel() {
		parcelLocation1to2.rewind();
		parcelLocation2to3.rewind();

		velocityParcel = new GeoVelocity(parcelLocation1to2);
	}

	@Test
	public void testGetAverageAccuracy() {
		velocity.update(location1);
		assertEquals(accuracy1, velocity.getAverageAccuracy(), 0);
		velocity.update(location2);
		assertEquals((accuracy1 + accuracy2) / 2, velocity.getAverageAccuracy(), 0);
		velocity.update(location3);
		assertEquals((accuracy1 + accuracy2 + accuracy3) / 3, velocity.getAverageAccuracy(), 0);

		assertEquals(accuracy1, velocityGeoLocation.getAverageAccuracy(), 0);
		velocityGeoLocation.update(location2);
		assertEquals((accuracy1 + accuracy2) / 2, velocityGeoLocation.getAverageAccuracy(), 0);
		velocityGeoLocation.update(location3);
		assertEquals((accuracy1 + accuracy2 + accuracy3) / 3, velocityGeoLocation.getAverageAccuracy(), 0);

		velocityParcel.update(location1);
		assertEquals(accuracy1, velocityParcel.getAverageAccuracy(), 0);
		velocityParcel.update(location2);
		assertEquals((accuracy1 + accuracy2) / 2, velocityParcel.getAverageAccuracy(), 0);
		velocityParcel.update(location3);
		assertEquals((accuracy1 + accuracy2 + accuracy3) / 3, velocityParcel.getAverageAccuracy(), 0);
	}

	@Test
	public void testGetBearing() {
		velocity.update(location1);
		velocity.update(location2);
		assertEquals(finalBearingLocation1to2, velocity.getBearing(), 0);
		velocity.update(location3);
		assertEquals(finalBearingLocation2to3, velocity.getBearing(), 0);

		velocityGeoLocation.update(location2);
		assertEquals(finalBearingLocation1to2, velocityGeoLocation.getBearing(), 0);
		velocityGeoLocation.update(location3);
		assertEquals(finalBearingLocation2to3, velocityGeoLocation.getBearing(), 0);

		assertEquals(finalBearingLocation1to2, velocityParcel.getBearing(), 0);
	}

	@Test
	public void testGetInitialBearing() {
		velocity.update(location1);
		velocity.update(location2);
		assertEquals(initBearingLocation1to2, velocity.getInitialBearing(), 0);
		velocity.update(location3);
		assertEquals(initBearingLocation2to3, velocity.getInitialBearing(), 0);

		velocityGeoLocation.update(location2);
		assertEquals(initBearingLocation1to2, velocityGeoLocation.getInitialBearing(), 0);
		velocityGeoLocation.update(location3);
		assertEquals(initBearingLocation2to3, velocityGeoLocation.getInitialBearing(), 0);

		assertEquals(0, velocityParcel.getInitialBearing(), 0);
	}

	@Test
	public void testGetParcelSize() {
		assertEquals(8, velocity.getParcelSize());
		assertEquals(8, velocityGeoLocation.getParcelSize());
		assertEquals(8, velocityParcel.getParcelSize());
	}

	@Test
	public void testGetSpeed() {
		velocity.update(location1);
		velocity.update(location2);
		assertEquals(speedLocation1to2, velocity.getSpeed(), 0);
		velocity.update(location3);
		assertEquals(speedLocation2to3, velocity.getSpeed(), 0);

		velocityGeoLocation.update(location2);
		assertEquals(speedLocation1to2, velocityGeoLocation.getSpeed(), 0);
		velocityGeoLocation.update(location3);
		assertEquals(speedLocation2to3, velocityGeoLocation.getSpeed(), 0);

		assertEquals(speedLocation1to2, velocityParcel.getSpeed(), 0);
	}

	@Test
	public void testParse() {
		parcelLocation2to3.rewind();
		velocity.parse(parcelLocation2to3);

		parcelLocation2to3.rewind();
		velocityGeoLocation.parse(parcelLocation2to3);

		parcelLocation2to3.rewind();
		velocityParcel.parse(parcelLocation2to3);

		assertArrayEquals(parcelArrayLocation2to3, velocity.toParcel().toByteArray());
		assertArrayEquals(parcelArrayLocation2to3, velocityGeoLocation.toParcel().toByteArray());
		assertArrayEquals(parcelArrayLocation2to3, velocityParcel.toParcel().toByteArray());
	}

	@Test
	public void testToParcel() {
		velocity.update(location1);
		velocity.update(location2);
		assertArrayEquals(parcelArrayLocation1to2, velocity.toParcel().toByteArray());
		velocity.update(location3);
		assertArrayEquals(parcelArrayLocation2to3, velocity.toParcel().toByteArray());

		velocityGeoLocation.update(location2);
		assertArrayEquals(parcelArrayLocation1to2, velocityGeoLocation.toParcel().toByteArray());
		velocityGeoLocation.update(location3);
		assertArrayEquals(parcelArrayLocation2to3, velocityGeoLocation.toParcel().toByteArray());

		assertArrayEquals(parcelArrayLocation1to2, velocityParcel.toParcel().toByteArray());
	}

	@Test
	public void testToString() {
		velocity.toString();
		velocityGeoLocation.toString();
		velocityParcel.toString();
	}

	@Test
	public void testUpdate() {
		velocity.update(location1);
		velocity.update(location2);
		assertArrayEquals(parcelArrayLocation1to2, velocity.toParcel().toByteArray());
		velocity.update(location3);
		assertArrayEquals(parcelArrayLocation2to3, velocity.toParcel().toByteArray());

		velocityGeoLocation.update(location2);
		assertArrayEquals(parcelArrayLocation1to2, velocityGeoLocation.toParcel().toByteArray());
		velocityGeoLocation.update(location3);
		assertArrayEquals(parcelArrayLocation2to3, velocityGeoLocation.toParcel().toByteArray());

		velocityParcel.update(location1);
		velocityParcel.update(location2);
		assertArrayEquals(parcelArrayLocation1to2, velocityParcel.toParcel().toByteArray());
		velocityParcel.update(location3);
		assertArrayEquals(parcelArrayLocation2to3, velocityParcel.toParcel().toByteArray());
	}

}
