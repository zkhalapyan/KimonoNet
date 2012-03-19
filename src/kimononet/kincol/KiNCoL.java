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
	
	/**
	 * Internal at which packets will be send from a random source to a random
	 * sink.
	 */
	private static final int PACKET_SENDING_INTERVAL = 1000;
	
	/**
	 * Numbers of seconds to allow beacon service to populate peers table prior
	 * to sending packets.
	 */
	private static final int INITIALIZATION_DELAY = 1000;
	
	/**
	 * The simulation will timeout with this specified value if the number of 
	 * packets to send ({@link #numberOfPackets} is zero. In other words, this
	 * timeout lets developers test out beacon service without sending any 
	 * data packets.
	 */
	private static final int BEACON_SERVICE_SIMULATION_TIMEOUT = 6000;
	
	/**
	 * Probability that at each interval an agent will explode. Value should be
	 * in the range of [0, 1].
	 */
	private final float hostilityFactor;
	
	/**
	 * Maximum number of packets to send out before killing all the services.
	 */
	private final float numberOfPackets;
	
	/**
	 * All the agents currently participating in the simulation.
	 */
	private final PeerAgent[] agents;
	
	/**
	 * Statistical monitor for all the agents participating in the current 
	 * simulation.
	 */
	private final StatMonitor statMonitor;
	
	/**
	 * The environment for all the peers participating in the simulation.
	 */
	private final PeerEnvironment peerEnvironment;
	
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
		}
		
	}

	
	public void run(){
		
		//The stat result will store the final results to be displayed to the 
		//user.
		StatResults results = new StatResults();
		
		//Start all services.
		for(PeerAgent agent : agents){
			agent.startServices();
		}
		
		
		try {
			
			//If no data packets are going to be sent, then sleep for a long 
			//time in order to get a chance to test out beacon service.
			if(numberOfPackets == 0){
				sleep(BEACON_SERVICE_SIMULATION_TIMEOUT);
			
			//If data packets are supposed to be sent, wait for just a bit so
			//that beacon service has enough time to populate its neighbor 
			//tables.
			}if(numberOfPackets > 0){
				sleep(INITIALIZATION_DELAY);
			}
			
			for(int i = 0; i < numberOfPackets; i++){
				
				//Account for agents exploding in hostile environments.
				for(PeerAgent agent : agents){
					if(Math.random() < hostilityFactor){
						killAgent(agent);
					}
				}
				
				//Get a random sender.
				PeerAgent source = agents[0];//getRandomPeerAgent();
				PeerAgent destination = agents[1];
				
				//Find a random receiver that is not the sender. 
				//while((destination = getRandomPeerAgent()) == source){}
				
				byte[] payload = new byte[]{0x01, 0x02};
				
				source.sendDataPacket(new DataPacket(source, destination.getPeer(), QualityOfService.REGULAR, payload));			
				
				results.combine(statMonitor.getStats().getStatResults(source.getPeer().getAddress(), destination.getPeer().getAddress()));
			
				//Slows down the simulation.
				sleep(PACKET_SENDING_INTERVAL);
				
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	

		killEveryone(agents);
		
		System.out.println(results);
		
	}

	/**
	 * Returns a random peer agent.
	 * @return a random peer agent.
	 */
	private PeerAgent getRandomPeerAgent(){
		return (agents.length == 0)? 
						null: 
						agents[(int)(Math.random() * agents.length)];
	}
	
	/**
	 * Shuts down all the services of the specified peer agent.
	 * @param agent The agent to kill.
	 */
	private void killAgent(PeerAgent agent){
		if(agent != null)
			agent.shutdownServices();
	}
	
	/**
	 * Kills all the agents specified.
	 * @param agents The agents to kill.
	 * @see #killAgent(PeerAgent)
	 */
	private void killEveryone(PeerAgent[] agents){
			
		for(PeerAgent agent : agents){
			killAgent(agent);
		}
	}
}
