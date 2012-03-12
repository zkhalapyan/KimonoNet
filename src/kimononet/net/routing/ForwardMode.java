package kimononet.net.routing;

import kimononet.net.parcel.Parcel;
import kimononet.net.parcel.Parcelable;

public enum ForwardMode implements Parcelable{
	
	GREEDY((byte)0x01),
	PERIMETER((byte)0x02);
	
	private static final int PARCEL_SIZE = 1;
	
	private byte flag;
	
	private ForwardMode(byte flag){
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
		return (flag==GREEDY.getFlag()?"GREEDY":"PERIMETER");
	}
}
