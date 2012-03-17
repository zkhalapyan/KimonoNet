package kimononet.kincol;

import kimononet.geo.GeoDevice;
import kimononet.geo.GeoLocation;
import kimononet.geo.GeoMap;
import kimononet.geo.GeoVelocity;
import kimononet.geo.RandomWaypointGeoDevice;
import kimononet.net.routing.QualityOfService;
import kimononet.net.transport.DataPacket;
import kimononet.peer.DefaultPeerEnvironment;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;
import kimononet.peer.PeerAgent;
import kimononet.peer.PeerEnvironment;
import kimononet.stat.MasterStatMonitor;
import kimononet.stat.StatMonitor;
import kimononet.stat.StatResults;

public class KiNCoL extends Thread{
	
	private static final int INTERVAL = 1000;
	
	private int sentPackets = 0;
	
	private float hostilityFactor;
	
	private float numberOfPackets;
	
	private PeerAgent[] agents;
	
	private StatMonitor statMonitor;
	
	private PeerEnvironment peerEnvironment;
	
	public KiNCoL(int numberOfPeers, 
				  GeoMap map, 
				  float peerSpeed, 
				  int numberOfPackets, 
				  float hostilityFactor){
		
		this.hostilityFactor = hostilityFactor;
		this.numberOfPackets = numberOfPackets;
		
		peerEnvironment = new DefaultPeerEnvironment();
		statMonitor = new MasterStatMonitor();;
		
		agents = new PeerAgent[numberOfPeers];
		
		for(int i = 0; i < numberOfPeers; i++){
			
			Peer peer = new Peer(PeerAddress.generateRandomAddress(), "peer-" + i);
			GeoLocation peerLocation = GeoLocation.generateRandomGeoLocation(map);
			GeoVelocity peerVelocity = new GeoVelocity(peerSpeed, GeoLocation.generateRandomBearing());
			GeoDevice geoDevice = new RandomWaypointGeoDevice(peerLocation, peerVelocity);
			PeerAgent agent = new PeerAgent(peer, peerEnvironment, geoDevice);
			agent.setStatMonitor(statMonitor);
			agents[i] = agent;
			agent.startServices();
		}
		
	}

	
	public void run(){
		
		StatResults results = new StatResults();
		
		while(sentPackets < numberOfPackets){
			
			//Get a random sender.
			PeerAgent source = getRandomPeerAgent();
			PeerAgent destination;
			
			//Find a random receiver that is not the sender. 
			while((destination = getRandomPeerAgent()) == source){}
			
			
			byte[] payload = new byte[]{0x01, 0x02};
			
			source.sendDataPacket(new DataPacket(source, destination.getPeer(), QualityOfService.REGULAR, payload));
			
			sentPackets ++;
			
			results.combine(statMonitor.getStats().getStatResults(source.getPeer().getAddress(), destination.getPeer().getAddress()));
		
			//Slows down the simulation.
			try {
				sleep(INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		killEveryone(agents);
		
		System.out.println(results);
		
	}

	private PeerAgent getRandomPeerAgent(){
		return agents[(int)(Math.random() * agents.length)];
	}
	
	private void killAgent(PeerAgent agent){
		agent.shutdownServices();
	}
	
	private void killEveryone(PeerAgent[] agents){
			
		for(PeerAgent agent : agents){
			killAgent(agent);
		}
	}
}
