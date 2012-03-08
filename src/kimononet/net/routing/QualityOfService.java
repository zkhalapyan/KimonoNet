package kimononet.net.routing;

import kimononet.net.parcel.Parcel;
import kimononet.net.parcel.Parcelable;

public enum QualityOfService implements Parcelable{
	
	HIGH((byte)0x01),
	MEDIUM((byte)0x02),
	LOW((byte)0x03);
	
	private static final int PARCEL_SIZE = 1;
	
	private byte flag;
	
	private QualityOfService(byte flag){
		this.flag = flag;
	}
	
	public byte getFlag(){
		return flag;
	}
	
	public Parcel toParcel(){
		return new Parcel(this.getFlag());
	}
	
	public int getParcelSize(){
		return PARCEL_SIZE;
	}
}
