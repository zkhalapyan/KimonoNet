package kimononet.net.p2p;

import kimononet.peer.Peer;
import kimononet.peer.PeerAgent;

public class BeaconPacket {

	public static final byte[] BEACON_PACKET_MAGIC_FLAG = new byte[] {(byte)0xF0, (byte)0x0B}; 
	public static final byte BEACON_PACKET_VERSION = (byte) 0x01;
	
	private PeerAgent agent;
	
	private BeaconType type = BeaconType.BEACON;
	
	public BeaconPacket(PeerAgent agent){
	
		this.agent = agent;
	}
	
	public void setType(BeaconType type){
		this.type = type;
	}
	
	public byte[] toByteArray(){
		
		Peer peer = agent.getPeer();
		
		byte[] address = peer.getAddress().toByteArray();
		
		//If peer's location is unknown, then the beacon cannot be constructed.
		if(peer.getLocation() == null){
			return null;
		}
		
		byte[] location = peer.getLocation().toByteArray();
		
		//Includes magic flag, version flag, type flag, address bytes 
		//and length bytes.
		byte[] beacon = new byte[
		                         	BEACON_PACKET_MAGIC_FLAG.length + 
		                         	2 + 
		                         	address.length + 
		                         	location.length
		                         ];
		
		int index = 0;
		
		//Copy the magic flag.
		System.arraycopy(BEACON_PACKET_MAGIC_FLAG, 0, beacon, index, BEACON_PACKET_MAGIC_FLAG.length);
		index += BEACON_PACKET_MAGIC_FLAG.length;
		
		//Copy packet version.
		beacon[index++] = BEACON_PACKET_VERSION;
		
		//Copy beacon type.
		beacon[index++] = type.getFlag();
		
		//Copy peer address.
		System.arraycopy(address, 0, beacon, index, address.length);
		index += address.length;
		
		//Copy peer location.
		System.arraycopy(location, 0, beacon, index, location.length);
		index += location.length;
		
		
		return beacon;
	}
	
	public String toString(){
		return agent.getPeer().getName() + " - " + 
			   agent.getPeer().getAddress() + " - " + 
			   agent.getPeer().getLocation();
	}
}
