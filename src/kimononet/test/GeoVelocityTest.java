package kimononet.test;

import static org.junit.Assert.*;
import kimononet.geo.GeoVelocity;
import kimononet.net.parcel.Parcel;

import org.junit.Before;
import org.junit.Test;

public class GeoVelocityTest {

	private final int parcelSize = 2*4;

	private final float speed1 = 123.4567890f;
	private final float bearing1 = 234.5678901f;
	private Parcel parcel1 = new Parcel(parcelSize);

	private final float speed2 = -123.4567890f;
	private final float bearing2 = -234.5678901f;
	private Parcel parcel2 = new Parcel(parcelSize);

	private GeoVelocity velocityFloatFloat;
	private GeoVelocity velocityParcel;

	@Before
	public void testGeoVelocityFloatFloat() {
		parcel1.rewind();

		parcel1.add(speed1);
		parcel1.add(bearing1);

		parcel1.rewind();

		velocityFloatFloat = new GeoVelocity(speed1, bearing1);
	}

	@Before
	public void testGeoVelocityParcel() {
		parcel2.rewind();

		parcel2.add(speed2);
		parcel2.add(bearing2);

		parcel2.rewind();

		velocityParcel = new GeoVelocity(parcel2);
	}

	@Test
	public void testGetSpeed() {
		assertEquals(speed1, velocityFloatFloat.getSpeed(), 0f);
		assertEquals(speed2, velocityParcel.getSpeed(), 0f);
	}

	@Test
	public void testGetBearing() {
		assertEquals(bearing1, velocityFloatFloat.getBearing(), 0f);
		assertEquals(bearing2, velocityParcel.getBearing(), 0f);
	}

	@Test
	public void testSetBearing() {
		velocityFloatFloat.setBearing(bearing2);
		velocityParcel.setBearing(bearing1);
		assertEquals(bearing2, velocityFloatFloat.getBearing(), 0f);
		assertEquals(bearing1, velocityParcel.getBearing(), 0f);
		assertEquals(speed1, velocityFloatFloat.getSpeed(), 0f);
		assertEquals(speed2, velocityParcel.getSpeed(), 0f);
	}

	@Test
	public void testParse() {
		parcel2.rewind();
		parcel1.rewind();
		velocityFloatFloat.parse(parcel2);
		velocityParcel.parse(parcel1);
		assertArrayEquals(parcel2.toByteArray(), velocityFloatFloat.toParcel().toByteArray());
		assertArrayEquals(parcel1.toByteArray(), velocityParcel.toParcel().toByteArray());
	}

	@Test
	public void testToParcel() {
		assertArrayEquals(parcel1.toByteArray(), velocityFloatFloat.toParcel().toByteArray());
		assertArrayEquals(parcel2.toByteArray(), velocityParcel.toParcel().toByteArray());
	}

	@Test
	public void testToString() {
		velocityFloatFloat.toString();
		velocityParcel.toString();
	}

	@Test
	public void testGetParcelSize() {
		assertEquals(parcelSize, velocityFloatFloat.getParcelSize());
		assertEquals(parcelSize, velocityParcel.getParcelSize());
	}

}
