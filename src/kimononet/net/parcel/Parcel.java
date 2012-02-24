package kimononet.net.parcel;

import kimononet.util.ByteManipulation;


public class Parcel implements Parcelable{

	private byte[] parcel;
	
	private int offset;
	
	public Parcel(int length){
		parcel = new byte[length];
	}
	
	public Parcel(byte parcel){
		this(new byte[]{parcel});
	}
	
	public Parcel(byte[] parcel){
		this.parcel = parcel;
		this.offset = parcel.length;
	}
	
	public void add(byte data){
		parcel[offset++] = data;
	}
	
	public void add(double data){
		add(ByteManipulation.toByteArray(data));
	}
	
	public void add(int data){
		add(ByteManipulation.toByteArray(data));
	}
	
	public void add(float data){
		add(ByteManipulation.toByteArray(data));
	}
	
	public void add(long data){
		add(ByteManipulation.toByteArray(data));
	}
	
	public void add(String data){
		add(ByteManipulation.toByteArray(data));
	}
	
	public void add(byte[] data){
		
		if(data != null){
			System.arraycopy(data, 0, parcel, offset, data.length);
			offset += data.length;	
		}
		
		
	}
	
	public void add(Parcelable parcelable){
		add(parcelable.toParcel());
	}
	
	public byte[] toByteArray(){
		return parcel;
	}
	
	public Parcel toParcel(){
		return this;
	}
	
	/**
	 * Returns current size of the parcel. To get the total capacity of the 
	 * parcel, use {@link #capacity()}.
	 * 
	 * @return The current size of the parcel.
	 */
	public int size(){
		return offset;
	}
	
	/**
	 * Returns the capacity of the current parcel. To get the current size of
	 * the parcel, use {@link #size()}.
	 * 
	 * @return The capacity of the current parcel.
	 */
	public int capacity(){
		return parcel.length;
	}
	
	/**
	 * Combines multiple parcelable objects into a single parcel.
	 * 
	 * @param parcels Various parcels to be combined.
	 * @return A byte array that represents all the specified parcels.
	 */
	public static Parcel combineParcelables(Parcelable... parcelables){
		
		
		Parcel[] parcels = new Parcel[parcelables.length];
		
		int totalParcelLength = 0;
		for(int i = 0; i < parcels.length; i++){
			parcels[i] = parcelables[i].toParcel();
			totalParcelLength += parcels[i].toByteArray().length;
		}
		
		Parcel parcel = new Parcel(totalParcelLength);
		
		for(int i = 0; i < parcels.length; i++){
			parcel.add(parcels[i]);
		}
		
		return parcel; 

		
	}
	
	
}
