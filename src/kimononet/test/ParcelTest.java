package kimononet.test;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import kimononet.net.parcel.Parcel;

import org.junit.Before;
import org.junit.Test;

public class ParcelTest {

	private Parcel parcelByte;
	private Parcel parcelByteArray;
	private Parcel parcelByteBuffer;
	private Parcel parcelInt;

	public ParcelTest() {
		testParcelByte();
		testParcelByteArray();
		testParcelByteBuffer();
		testParcelInt();
	}

	@Before
	public void testParcelByte() {
		parcelByte = new Parcel((byte)0x29);
	}

	@Before
	public void testParcelByteArray() {
		parcelByteArray = new Parcel(new byte[] {(byte)0x99, (byte)0x35});
	}

	@Before
	public void testParcelByteBuffer() {
		parcelByteBuffer = new Parcel(ByteBuffer.allocate(16));
	}

	@Before
	public void testParcelInt() {
		parcelInt = new Parcel((int)16);
	}

	@Test
	public void testAddByte() {
		parcelByte.rewind();
		parcelByte.add((byte)0xC1);
		assertEquals(1, parcelByte.capacity());
		assertEquals(1, parcelByte.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xC1}, parcelByte.toByteArray());

		parcelByteArray.rewind();
		parcelByteArray.add((byte)0x8E);
		assertEquals(2, parcelByteArray.capacity());
		assertEquals(1, parcelByteArray.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x8E, (byte)0x35}, parcelByteArray.toByteArray());

		parcelByteArray.add((byte)0xEB);
		assertEquals(2, parcelByteArray.capacity());
		assertEquals(2, parcelByteArray.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x8E, (byte)0xEB}, parcelByteArray.toByteArray());

		parcelByteBuffer.add((byte)0x8C);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(1, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x8C, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelByteBuffer.add((byte)0x7B);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(2, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x8C, (byte)0x7B, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelInt.add((byte)0x1C);
		assertEquals(16, parcelInt.capacity());
		assertEquals(1, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x1C, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((byte)0x94);
		assertEquals(16, parcelInt.capacity());
		assertEquals(2, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x1C, (byte)0x94, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddByteArray() {
		parcelByte.rewind();
		parcelByte.add(new byte[] {(byte)0x3A});
		assertEquals(1, parcelByte.capacity());
		assertEquals(1, parcelByte.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x3A}, parcelByte.toByteArray());

		parcelByteArray.rewind();
		parcelByteArray.add(new byte[] {(byte)0x1C, (byte)0xF8});
		assertEquals(2, parcelByteArray.capacity());
		assertEquals(2, parcelByteArray.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x1C, (byte)0xF8}, parcelByteArray.toByteArray());

		parcelByteBuffer.add(new byte[] {(byte)0xE5, (byte)0xBD, (byte)0x49});
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(3, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xE5, (byte)0xBD, (byte)0x49, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelByteBuffer.add(new byte[] {(byte)0x9F, (byte)0xF1, (byte)0xEC});
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(6, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xE5, (byte)0xBD, (byte)0x49, (byte)0x9F, (byte)0xF1, (byte)0xEC, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelInt.add(new byte[] {(byte)0xAA, (byte)0x15, (byte)0x90});
		assertEquals(16, parcelInt.capacity());
		assertEquals(3, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xAA, (byte)0x15, (byte)0x90, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add(new byte[] {(byte)0x41, (byte)0xAF, (byte)0xCD});
		assertEquals(16, parcelInt.capacity());
		assertEquals(6, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xAA, (byte)0x15, (byte)0x90, (byte)0x41, (byte)0xAF, (byte)0xCD, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddChar() {
		parcelByteArray.rewind();
		parcelByteArray.add((char)'A');
		assertEquals(2, parcelByteArray.capacity());
		assertEquals(2, parcelByteArray.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)'A'}, parcelByteArray.toByteArray());

		parcelByteBuffer.add((char)'B');
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(1*2, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)'B', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelByteBuffer.add((char)'C');
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(2*2, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)'B', (byte)0x00, (byte)'C', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelInt.add((char)'D');
		assertEquals(16, parcelInt.capacity());
		assertEquals(1*2, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)'D', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((char)'E');
		assertEquals(16, parcelInt.capacity());
		assertEquals(2*2, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)'D', (byte)0x00, (byte)'E', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddDouble() {
		parcelByteBuffer.add((double)123.4567890d);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(1*8, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x40, (byte)0x5E, (byte)0xDD, (byte)0x3C, (byte)0x07, (byte)0xEE, (byte)0x0B, (byte)0x0B, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelByteBuffer.add((double)234.5678901d);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(2*8, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x40, (byte)0x5E, (byte)0xDD, (byte)0x3C, (byte)0x07, (byte)0xEE, (byte)0x0B, (byte)0x0B, (byte)0x40, (byte)0x6D, (byte)0x52, (byte)0x2C, (byte)0x27, (byte)0xDB, (byte)0xE7, (byte)0x1C}, parcelByteBuffer.toByteArray());

		parcelInt.add((double)-123.4567890d);
		assertEquals(16, parcelInt.capacity());
		assertEquals(1*8, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xC0, (byte)0x5E, (byte)0xDD, (byte)0x3C, (byte)0x07, (byte)0xEE, (byte)0x0B, (byte)0x0B, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((double)-234.5678901d);
		assertEquals(16, parcelInt.capacity());
		assertEquals(2*8, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xC0, (byte)0x5E, (byte)0xDD, (byte)0x3C, (byte)0x07, (byte)0xEE, (byte)0x0B, (byte)0x0B, (byte)0xC0, (byte)0x6D, (byte)0x52, (byte)0x2C, (byte)0x27, (byte)0xDB, (byte)0xE7, (byte)0x1C}, parcelInt.toByteArray());
	}

	@Test
	public void testAddFloat() {
		parcelByteBuffer.add((float)123.4567890f);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(1*4, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x42, (byte)0xF6, (byte)0xE9, (byte)0xE0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelByteBuffer.add((float)234.5678901f);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(2*4, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x42, (byte)0xF6, (byte)0xE9, (byte)0xE0, (byte)0x43, (byte)0x6A, (byte)0x91, (byte)0x61, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelInt.add((float)-123.4567890f);
		assertEquals(16, parcelInt.capacity());
		assertEquals(1*4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xC2, (byte)0xF6, (byte)0xE9, (byte)0xE0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((float)-234.5678901f);
		assertEquals(16, parcelInt.capacity());
		assertEquals(2*4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xC2, (byte)0xF6, (byte)0xE9, (byte)0xE0, (byte)0xC3, (byte)0x6A, (byte)0x91, (byte)0x61, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddInt() {
		parcelByteBuffer.add((int)1234567890);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(1*4, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelByteBuffer.add((int)234567890);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(2*4, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2, (byte)0x0D, (byte)0xFB, (byte)0x38, (byte)0xD2, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelInt.add((int)-1234567890);
		assertEquals(16, parcelInt.capacity());
		assertEquals(1*4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xB6, (byte)0x69, (byte)0xFD, (byte)0x2E, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((int)-234567890);
		assertEquals(16, parcelInt.capacity());
		assertEquals(2*4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xB6, (byte)0x69, (byte)0xFD, (byte)0x2E, (byte)0xF2, (byte)0x04, (byte)0xC7, (byte)0x2E, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddIntLong() {
		parcelByteBuffer.add((int)8, (long)1234567890l);
		assertEquals(16, parcelByteBuffer.capacity());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2}, parcelByteBuffer.toByteArray());

		parcelByteBuffer.add((int)0, (long)2345678901l);
		assertEquals(16, parcelByteBuffer.capacity());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x8B, (byte)0xD0, (byte)0x38, (byte)0x35, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2}, parcelByteBuffer.toByteArray());

		parcelInt.add((int)8, (long)-1234567890l);
		assertEquals(16, parcelInt.capacity());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xB6, (byte)0x69, (byte)0xFD, (byte)0x2E}, parcelInt.toByteArray());

		parcelInt.add((int)0, (long)-2345678901l);
		assertEquals(16, parcelInt.capacity());
		assertArrayEquals(new byte[] {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x74, (byte)0x2F, (byte)0xC7, (byte)0xCB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xB6, (byte)0x69, (byte)0xFD, (byte)0x2E}, parcelInt.toByteArray());
	}

	@Test
	public void testAddLong() {
		parcelByteBuffer.add((long)1234567890l);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(1*8, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelByteBuffer.add((long)2345678901l);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(2*8, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x49, (byte)0x96, (byte)0x02, (byte)0xD2, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x8B, (byte)0xD0, (byte)0x38, (byte)0x35}, parcelByteBuffer.toByteArray());

		parcelInt.add((long)-1234567890l);
		assertEquals(16, parcelInt.capacity());
		assertEquals(1*8, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xB6, (byte)0x69, (byte)0xFD, (byte)0x2E, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((long)-2345678901l);
		assertEquals(16, parcelInt.capacity());
		assertEquals(2*8, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xB6, (byte)0x69, (byte)0xFD, (byte)0x2E, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x74, (byte)0x2F, (byte)0xC7, (byte)0xCB}, parcelInt.toByteArray());
	}

	@Test
	public void testAddParcelable() {
		parcelByte.rewind();
		parcelByte.add(new Parcel(new byte[] {(byte)0x3A}));
		assertEquals(1, parcelByte.capacity());
		assertEquals(1, parcelByte.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x3A}, parcelByte.toByteArray());

		parcelByteArray.rewind();
		parcelByteArray.add(new Parcel(new byte[] {(byte)0x1C, (byte)0xF8}));
		assertEquals(2, parcelByteArray.capacity());
		assertEquals(2, parcelByteArray.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x1C, (byte)0xF8}, parcelByteArray.toByteArray());

		parcelByteBuffer.add(new Parcel(new byte[] {(byte)0x28, (byte)0x1A, (byte)0x77, (byte)0x71}));
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(4, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x28, (byte)0x1A, (byte)0x77, (byte)0x71, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelByteBuffer.add(new Parcel(new byte[] {(byte)0x2A, (byte)0x35, (byte)0x92, (byte)0x4B}));
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(8, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x28, (byte)0x1A, (byte)0x77, (byte)0x71, (byte)0x2A, (byte)0x35, (byte)0x92, (byte)0x4B, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelInt.add(new Parcel(new byte[] {(byte)0xE1, (byte)0xFA, (byte)0x8A, (byte)0x47}));
		assertEquals(16, parcelInt.capacity());
		assertEquals(4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xE1, (byte)0xFA, (byte)0x8A, (byte)0x47, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add(new Parcel(new byte[] {(byte)0x8E, (byte)0x27, (byte)0x79, (byte)0x08}));
		assertEquals(16, parcelInt.capacity());
		assertEquals(8, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xE1, (byte)0xFA, (byte)0x8A, (byte)0x47, (byte)0x8E, (byte)0x27, (byte)0x79, (byte)0x08, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testAddString() {
		parcelByte.rewind();
		parcelByte.add((String)"a");
		assertEquals(1, parcelByte.capacity());
		assertEquals(1, parcelByte.getParcelSize());
		assertArrayEquals(new byte[] {(byte)'a'}, parcelByte.toByteArray());

		parcelByteArray.rewind();
		parcelByteArray.add((String)"ab");
		assertEquals(2, parcelByteArray.capacity());
		assertEquals(2, parcelByteArray.getParcelSize());
		assertArrayEquals(new byte[] {(byte)'a', (byte)'b'}, parcelByteArray.toByteArray());

		parcelByteBuffer.add((String)"foo");
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(3, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)'f', (byte)'o', (byte)'o', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelByteBuffer.add((String)"bar");
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(6, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)'f', (byte)'o', (byte)'o', (byte)'b', (byte)'a', (byte)'r', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelInt.add((String)"baz");
		assertEquals(16, parcelInt.capacity());
		assertEquals(3, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)'b', (byte)'a', (byte)'z', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());

		parcelInt.add((String)"qux");
		assertEquals(16, parcelInt.capacity());
		assertEquals(6, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)'b', (byte)'a', (byte)'z', (byte)'q', (byte)'u', (byte)'x', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testCapacity() {
		assertEquals(1, parcelByte.capacity());
		assertEquals(2, parcelByteArray.capacity());
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(16, parcelInt.capacity());
	}

	@Test
	public void testCompact() {
		parcelByteBuffer.add(new byte[] {(byte)0x99, (byte)0x65, (byte)0xC9, (byte)0x53, (byte)0x43, (byte)0x14, (byte)0x89, (byte)0x23, (byte)0x1F, (byte)0x8B, (byte)0x0C, (byte)0x03, (byte)0x81, (byte)0x63, (byte)0x58, (byte)0xFD});
		parcelInt.add(new byte[] {(byte)0xD8, (byte)0x09, (byte)0x55, (byte)0x8B, (byte)0x46, (byte)0x84, (byte)0xCD, (byte)0xF5, (byte)0xBD, (byte)0x04, (byte)0x18, (byte)0x3A, (byte)0xA8, (byte)0x92, (byte)0xD4, (byte)0xA5});

		parcelByteBuffer.rewind();
		parcelInt.rewind();

		parcelByteBuffer.add(new byte[] {(byte)0xD8, (byte)0x48});
		parcelInt.add(new byte[] {(byte)0x39, (byte)0xED});

		assertEquals(1, parcelByte.capacity());
		assertEquals(2, parcelByteArray.capacity());
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(16, parcelInt.capacity());

		assertEquals(1, parcelByte.getParcelSize());
		assertEquals(2, parcelByteArray.getParcelSize());
		assertEquals(2, parcelByteBuffer.getParcelSize());
		assertEquals(2, parcelInt.getParcelSize());

		assertArrayEquals(new byte[] {(byte)0x29}, parcelByte.toByteArray());
		assertArrayEquals(new byte[] {(byte)0x99, (byte)0x35}, parcelByteArray.toByteArray());
		assertArrayEquals(new byte[] {(byte)0xD8, (byte)0x48, (byte)0xC9, (byte)0x53, (byte)0x43, (byte)0x14, (byte)0x89, (byte)0x23, (byte)0x1F, (byte)0x8B, (byte)0x0C, (byte)0x03, (byte)0x81, (byte)0x63, (byte)0x58, (byte)0xFD}, parcelByteBuffer.toByteArray());
		assertArrayEquals(new byte[] {(byte)0x39, (byte)0xED, (byte)0x55, (byte)0x8B, (byte)0x46, (byte)0x84, (byte)0xCD, (byte)0xF5, (byte)0xBD, (byte)0x04, (byte)0x18, (byte)0x3A, (byte)0xA8, (byte)0x92, (byte)0xD4, (byte)0xA5}, parcelInt.toByteArray());

		parcelByte.compact();
		parcelByteArray.compact();
		parcelByteBuffer.compact();
		parcelInt.compact();

		assertEquals(1, parcelByte.capacity());
		assertEquals(2, parcelByteArray.capacity());
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(16, parcelInt.capacity());

		// Buffer positions per documentation for ByteBuffer.compact().
		assertEquals(1-1, parcelByte.getParcelSize());
		assertEquals(2-2, parcelByteArray.getParcelSize());
		assertEquals(16-2, parcelByteBuffer.getParcelSize());
		assertEquals(16-2, parcelInt.getParcelSize());

		// It seems ByteBuffer.compact() does not erase the free bytes, so must take that into consideration.
		assertArrayEquals(new byte[] {(byte)0x29}, parcelByte.toByteArray());
		assertArrayEquals(new byte[] {(byte)0x99, (byte)0x35}, parcelByteArray.toByteArray());
		assertArrayEquals(new byte[] {(byte)0xC9, (byte)0x53, (byte)0x43, (byte)0x14, (byte)0x89, (byte)0x23, (byte)0x1F, (byte)0x8B, (byte)0x0C, (byte)0x03, (byte)0x81, (byte)0x63, (byte)0x58, (byte)0xFD, (byte)0x58, (byte)0xFD}, parcelByteBuffer.toByteArray());
		assertArrayEquals(new byte[] {(byte)0x55, (byte)0x8B, (byte)0x46, (byte)0x84, (byte)0xCD, (byte)0xF5, (byte)0xBD, (byte)0x04, (byte)0x18, (byte)0x3A, (byte)0xA8, (byte)0x92, (byte)0xD4, (byte)0xA5, (byte)0xD4, (byte)0xA5}, parcelInt.toByteArray());
	}

	@Test
	public void testCopy() {
		parcelByte.rewind();
		parcelByte.copy(new byte[] {(byte)0xE3, (byte)0x5A}, 0, 1);
		assertEquals(1, parcelByte.capacity());
		assertEquals(1, parcelByte.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0xE3}, parcelByte.toByteArray());

		parcelByteArray.rewind();
		parcelByteArray.copy(new byte[] {(byte)0xE3, (byte)0x5A}, 1, 1);
		assertEquals(2, parcelByteArray.capacity());
		assertEquals(1, parcelByteArray.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x5A, (byte)0x35}, parcelByteArray.toByteArray());

		parcelByteBuffer.copy(new byte[] {(byte)0xBE, (byte)0x5A, (byte)0x89}, 1, 2);
		assertEquals(16, parcelByteBuffer.capacity());
		assertEquals(2, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x5A, (byte)0x89, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelByteBuffer.toByteArray());

		parcelInt.copy(new byte[] {(byte)0x4D, (byte)0x94, (byte)0x2F, (byte)0xD5}, 0, 3);
		assertEquals(16, parcelInt.capacity());
		assertEquals(3, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x4D, (byte)0x94, (byte)0x2F, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, parcelInt.toByteArray());
	}

	@Test
	public void testGetByte() {
		parcelByte.rewind();
		assertEquals((byte)0x29, parcelByte.getByte());

		parcelByteArray.rewind();
		assertEquals((byte)0x99, parcelByteArray.getByte());
		assertEquals((byte)0x35, parcelByteArray.getByte());

		parcelByteBuffer.add((byte)0xB6);
		assertEquals((byte)0x00, parcelByteBuffer.getByte());
		parcelByteBuffer.rewind();
		assertEquals((byte)0xB6, parcelByteBuffer.getByte());
		assertEquals((byte)0x00, parcelByteBuffer.getByte());

		parcelInt.add((byte)0xFD);
		assertEquals((byte)0x00, parcelInt.getByte());
		parcelInt.rewind();
		assertEquals((byte)0xFD, parcelInt.getByte());
		assertEquals((byte)0x00, parcelInt.getByte());
	}

	@Test
	public void testGetByteArray() {
		byte[] data1 = new byte[1];
		byte[] data2 = new byte[2];

		parcelByte.rewind();
		parcelByte.getByteArray(data1);
		assertArrayEquals(new byte[] {(byte)0x29}, data1);

		parcelByteArray.rewind();
		parcelByteArray.getByteArray(data2);
		assertArrayEquals(new byte[] {(byte)0x99, (byte)0x35}, data2);
		parcelByteArray.rewind();
		parcelByteArray.getByteArray(data1);
		assertArrayEquals(new byte[] {(byte)0x99}, data1);
		parcelByteArray.getByteArray(data1);
		assertArrayEquals(new byte[] {(byte)0x35}, data1);

		parcelByteBuffer.add(new byte[] {(byte)0x9A, (byte)0xA2});
		parcelByteBuffer.getByteArray(data2);
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00}, data2);
		parcelByteBuffer.rewind();
		parcelByteBuffer.getByteArray(data2);
		assertArrayEquals(new byte[] {(byte)0x9A, (byte)0xA2}, data2);
		parcelByteBuffer.getByteArray(data2);
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00}, data2);

		parcelInt.add(new byte[] {(byte)0x2D, (byte)0x91});
		parcelInt.getByteArray(data2);
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00}, data2);
		parcelInt.rewind();
		parcelInt.getByteArray(data2);
		assertArrayEquals(new byte[] {(byte)0x2D, (byte)0x91}, data2);
		parcelByteBuffer.getByteArray(data2);
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00}, data2);
	}

	@Test
	public void testGetChar() {
		parcelByteBuffer.add((char)'A');
		parcelByteBuffer.add((char)'B');
		parcelByteBuffer.rewind();
		assertEquals((char)'A', parcelByteBuffer.getChar());
		assertEquals((char)'B', parcelByteBuffer.getChar());

		parcelInt.add((char)'C');
		parcelInt.add((char)'D');
		parcelInt.rewind();
		assertEquals((char)'C', parcelInt.getChar());
		assertEquals((char)'D', parcelInt.getChar());
	}

	@Test
	public void testGetDouble() {
		parcelByteBuffer.add((double)123.4567890d);
		parcelByteBuffer.add((double)234.5678901d);
		parcelByteBuffer.rewind();
		assertEquals((double)123.4567890d, parcelByteBuffer.getDouble(), 0);
		assertEquals((double)234.5678901d, parcelByteBuffer.getDouble(), 0);

		parcelInt.add((double)-123.4567890d);
		parcelInt.add((double)-234.5678901d);
		parcelInt.rewind();
		assertEquals((double)-123.4567890d, parcelInt.getDouble(), 0);
		assertEquals((double)-234.5678901d, parcelInt.getDouble(), 0);
	}

	@Test
	public void testGetFloat() {
		parcelByteBuffer.add((float)123.4567890f);
		parcelByteBuffer.add((float)234.5678901f);
		parcelByteBuffer.rewind();
		assertEquals((float)123.4567890f, parcelByteBuffer.getFloat(), 0);
		assertEquals((float)234.5678901f, parcelByteBuffer.getFloat(), 0);

		parcelInt.add((float)-123.4567890f);
		parcelInt.add((float)-234.5678901f);
		parcelInt.rewind();
		assertEquals((float)-123.4567890f, parcelInt.getFloat(), 0);
		assertEquals((float)-234.5678901f, parcelInt.getFloat(), 0);
	}

	@Test
	public void testGetInt() {
		parcelByteBuffer.add((int)123);
		parcelByteBuffer.add((int)234);
		parcelByteBuffer.rewind();
		assertEquals((int)123, parcelByteBuffer.getInt());
		assertEquals((int)234, parcelByteBuffer.getInt());

		parcelInt.add((int)-123);
		parcelInt.add((int)-234);
		parcelInt.rewind();
		assertEquals((int)-123, parcelInt.getInt());
		assertEquals((int)-234, parcelInt.getInt());
	}

	@Test
	public void testGetLong() {
		parcelByteBuffer.add((long)1234567890l);
		parcelByteBuffer.add((long)2345678901l);
		parcelByteBuffer.rewind();
		assertEquals((long)1234567890l, parcelByteBuffer.getLong());
		assertEquals((long)2345678901l, parcelByteBuffer.getLong());

		parcelInt.add((long)-1234567890l);
		parcelInt.add((long)-2345678901l);
		parcelInt.rewind();
		assertEquals((long)-1234567890l, parcelInt.getLong());
		assertEquals((long)-2345678901l, parcelInt.getLong());
	}

	@Test
	public void testGetParcelSize() {
		assertEquals(1, parcelByte.getParcelSize());
		assertEquals(2, parcelByteArray.getParcelSize());
		assertEquals(0, parcelByteBuffer.getParcelSize());
		assertEquals(0, parcelInt.getParcelSize());
	}

	@Test
	public void testRewind() {
		parcelByte.rewind();
		assertEquals(0, parcelByte.getParcelSize());

		parcelByteArray.rewind();
		assertEquals(0, parcelByteArray.getParcelSize());

		parcelByteBuffer.add((byte)0x5A);
		parcelByteBuffer.rewind();
		assertEquals(0, parcelByteBuffer.getParcelSize());

		parcelInt.add((byte)0x25);
		parcelInt.rewind();
		assertEquals(0, parcelInt.getParcelSize());
	}

	@Test
	public void testSetParcel() {
		Parcel parcelNew1 = new Parcel(new byte[] {(byte)0x25, (byte)0x19, (byte)0xB1, (byte)0x17});
		Parcel parcelNew2 = new Parcel(new byte[] {(byte)0x54, (byte)0x8D, (byte)0xC7, (byte)0x1C});
		Parcel parcelNew3 = new Parcel(new byte[] {(byte)0x0F, (byte)0x64, (byte)0x01, (byte)0xA7});
		Parcel parcelNew4 = new Parcel(new byte[] {(byte)0x73, (byte)0xDC, (byte)0x44, (byte)0x8D});

		parcelByte.setParcel(parcelNew1);
		parcelByteArray.setParcel(parcelNew2);
		parcelByteBuffer.setParcel(parcelNew3);
		parcelInt.setParcel(parcelNew4);

		assertEquals(4, parcelByte.capacity());
		assertEquals(4, parcelByte.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x25, (byte)0x19, (byte)0xB1, (byte)0x17}, parcelByte.toByteArray());

		assertEquals(4, parcelByteArray.capacity());
		assertEquals(4, parcelByteArray.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x54, (byte)0x8D, (byte)0xC7, (byte)0x1C}, parcelByteArray.toByteArray());

		assertEquals(4, parcelByteBuffer.capacity());
		assertEquals(4, parcelByteBuffer.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x0F, (byte)0x64, (byte)0x01, (byte)0xA7}, parcelByteBuffer.toByteArray());

		assertEquals(4, parcelInt.capacity());
		assertEquals(4, parcelInt.getParcelSize());
		assertArrayEquals(new byte[] {(byte)0x73, (byte)0xDC, (byte)0x44, (byte)0x8D}, parcelInt.toByteArray());
	}

	@Test
	public void testSlice() {
		byte[] data;
		Parcel parcelSlice;

		parcelByte.rewind();
		parcelSlice = parcelByte.slice();
		assertEquals(1, parcelSlice.capacity());
		data = new byte[parcelSlice.capacity()];
		parcelSlice.getByteArray(data);
		assertArrayEquals(new byte[] {0x29}, data);

		parcelByteArray.rewind();
		parcelByteArray.add((byte)0xDE);
		parcelSlice = parcelByteArray.slice();
		assertEquals(1, parcelSlice.capacity());
		data = new byte[parcelSlice.capacity()];
		parcelSlice.getByteArray(data);
		assertArrayEquals(new byte[] {0x35}, data);

		parcelByteBuffer.add(new byte[] {(byte)0xAC, (byte)0x14, (byte)0xD9, (byte)0xF1});
		parcelSlice = parcelByteBuffer.slice();
		assertEquals(12, parcelSlice.capacity());
		data = new byte[parcelSlice.capacity()];
		parcelSlice.getByteArray(data);
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, data);

		parcelInt.add(new byte[] {(byte)0x17, (byte)0xB3, (byte)0xCD, (byte)0x95, (byte)0xDB});
		parcelSlice = parcelInt.slice();
		assertEquals(11, parcelSlice.capacity());
		data = new byte[parcelSlice.capacity()];
		parcelSlice.getByteArray(data);
		assertArrayEquals(new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}, data);
	}

	@Test
	public void testToByteArray() {
		assertArrayEquals(new byte[] {(byte)0x29}, parcelByte.toByteArray());
		assertArrayEquals(new byte[] {(byte)0x99, (byte)0x35}, parcelByteArray.toByteArray());
		assertArrayEquals(new byte[16], parcelByteBuffer.toByteArray());
		assertArrayEquals(new byte[16], parcelInt.toByteArray());
	}

	@Test
	public void testToParcel() {
		assertEquals(parcelByte, parcelByte.toParcel());
		assertEquals(parcelByteArray, parcelByteArray.toParcel());
		assertEquals(parcelByteBuffer, parcelByteBuffer.toParcel());
		assertEquals(parcelInt, parcelInt.toParcel());
	}

	@Test
	public void testCombineParcelables() {
		Parcel parcel = Parcel.combineParcelables(new Parcel(new byte[] {(byte)0xD7, (byte)0xD4, (byte)0x2A, (byte)0x2B}), new Parcel(new byte[] {(byte)0x1E, (byte)0x8B, (byte)0xB0, (byte)0xC1}));
		assertArrayEquals(new byte[] {(byte)0xD7, (byte)0xD4, (byte)0x2A, (byte)0x2B, (byte)0x1E, (byte)0x8B, (byte)0xB0, (byte)0xC1}, parcel.toByteArray());
	}

}
