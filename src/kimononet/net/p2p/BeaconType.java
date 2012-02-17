package kimononet.net.p2p;

public enum BeaconType {
	
	BEACON((byte)0x01),
	HANDSHAKE((byte)0x01);
	
	private byte flag;
	
	private BeaconType(byte flag){
		this.flag = flag;
	}
	
	public byte getFlag(){
		return this.flag;
	}
}
