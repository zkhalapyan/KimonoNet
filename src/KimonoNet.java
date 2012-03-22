import kimononet.geo.GeoLocation;
import kimononet.geo.GeoMap;
import kimononet.geo.GeoVelocity;
import kimononet.kincol.KiNCoL;
import kimononet.log.LogType;
import kimononet.log.Logger;
import kimononet.net.Packet;
import kimononet.net.PacketType;
import kimononet.net.beacon.BeaconPacket;
import kimononet.net.p2p.Connection;
import kimononet.net.p2p.MulticastConnection;
import kimononet.net.p2p.port.PortConfiguration;
import kimononet.net.parcel.Parcel;
import kimononet.net.routing.ForwardMode;
import kimononet.net.routing.QualityOfService;
import kimononet.net.transport.DataPacket;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;
import kimononet.peer.PeerAgent;
import kimononet.simulation.Simulation;
import kimononet.stat.MasterStatMonitor;
import kimononet.stat.StatMonitor;
import kimononet.stat.StatPacket;


@SuppressWarnings("unused")
public class KimonoNet {

	private static final boolean DEBUG_ON = false;
	private static final boolean INFO_ON = true;
	private static final boolean ERROR_ON = false;
	
	public static void startUISimulation(){
		
		Simulation simulation = new Simulation();
		simulation.start();
	}
	
	public static void main(String args[]){
		
		Logger.setDebug(DEBUG_ON);
		Logger.setInfo(INFO_ON);
		Logger.setError(ERROR_ON);
		
		if(args.length <= 0 || args[0].equals("mode-gui")){
			startUISimulation();
			
		}else if(args[0].equals("mode-cl") || args[0].equals("mode-cl-gpsr")){
			
			if(args.length < 7){
				Logger.log("Please specify [number-of-peers map-width map-height hostility-factor peer-speed number-of-packets [beacon-timeout]].", LogType.INFO);
				return;
			}
			
			//Extract command line properties.
			int   numberOfPeers    = Integer.parseInt(args[1]);
			int   mapWidth         = Integer.parseInt(args[2]);
			int   mapHeight        = Integer.parseInt(args[3]);
			float hostilityFactor  = Float.parseFloat(args[4]);
			float peerSpeed        = Float.parseFloat(args[5]);
			int   numberOfPackets  = Integer.parseInt(args[6]);
			int   beaconTimeout    = (args.length > 7)? Integer.parseInt(args[7]) : -1;
			boolean gpsrSimulation =  args[0].equals("mode-cl-gpsr");
			
			//Warn the user that to send packets between a source and a sink, at
			//least two nodes are required.
			if(numberOfPeers < 2){
				
				Logger.log("Please specify more than 2 peers in order " +
						   "to be able to send and receive packets. No " +
						   "packets will be sent.", LogType.INFO);
				
				numberOfPackets = 0;
			}
			
			
			GeoMap map = new GeoMap(mapWidth, mapHeight);
			
			//Create and start a new KiNCoL session.
			new KiNCoL(numberOfPeers, 
					   map, 
					   peerSpeed, 
					   numberOfPackets, 
					   hostilityFactor, 
					   beaconTimeout,
					   gpsrSimulation).start();
			
		}else{
			Logger.log("Please specify [number-of-peers map-width map-height hostility-factor peer-speed number-of-packets [beacon-timeout]].", LogType.INFO);
		}
		
	}

}
