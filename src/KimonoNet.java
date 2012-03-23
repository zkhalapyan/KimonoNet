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
			
			int   numberOfPeers;
			int   mapWidth;
			int   mapHeight;
			float hostilityFactor;
			float peerSpeed;
			int   numberOfPackets;
			int   beaconTimeout;
			boolean gpsrSimulation;
			
			
			//Specifying only the execution mode, runs the 
			//default configuration.
			if(args.length == 1){
				Logger.info("");
				Logger.info("--No Arguments Specified: " +
						    "Running Difault Simulation--");
				Logger.info("");
				
				//Default simulation configuration.
				numberOfPeers    = 27;
				mapWidth         = 450;
				mapHeight        = 450;
				hostilityFactor  = 0;
				peerSpeed        = 15;
				numberOfPackets  = 100;
				beaconTimeout    = 10000;
				gpsrSimulation   = false;
				
			//If the user only impartially defined the simulation arguments, 
			//then exit with an error.
			} else if(args.length < 7){
				Logger.info("Please specify [number-of-peers map-width " +
						    "map-height hostility-factor peer-speed " +
						    "number-of-packets [beacon-timeout]].");
				return;
			
			//If the required number of arguments was specified, then try to 
			//parse the simulation parameters.
			} else{
				
				//Extract command line properties.
				numberOfPeers    = Integer.parseInt(args[1]);
				mapWidth         = Integer.parseInt(args[2]);
				mapHeight        = Integer.parseInt(args[3]);
				hostilityFactor  = Float.parseFloat(args[4]);
				peerSpeed        = Float.parseFloat(args[5]);
				numberOfPackets  = Integer.parseInt(args[6]);
				beaconTimeout    = (args.length > 7)?Integer.parseInt(args[7]):-1;
				gpsrSimulation =  args[0].equals("mode-cl-gpsr");
				
			}
			

			
			//Warn the user that to send packets between a source and a sink, at
			//least two nodes are required.
			if(numberOfPeers < 2){
				
				Logger.info("");
				Logger.info("Specify more than 2 peers in order " +
						    "to be able to send and receive packets.");
				Logger.info("");
				
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
			Logger.info("Please specify [number-of-peers map-width " +
					    "map-height hostility-factor peer-speed " +
					    "number-of-packets [beacon-timeout]].");
		}
		
	}

}
