package kimononet.net.routing;

import kimononet.net.parcel.Parcel;
import kimononet.net.parcel.Parcelable;

public enum QualityOfService implements Parcelable{
	
	CONTROL((byte)0x01),
	COMMUNICATION((byte)0x02),
	REGULAR((byte)0x03);
	
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
	
	public String toString(){
		return (flag==CONTROL.getFlag()?"CONTROL":
			(flag==COMMUNICATION.getFlag()?"COMMUNICATION":"REGULAR") );
	}
}
