import kimononet.geo.GeoLocation;
import kimononet.geo.GeoMap;
import kimononet.geo.GeoVelocity;
import kimononet.kincol.KiNCoL;
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

	public static void startUISimulation(){
		
		Simulation simulation = new Simulation();
		simulation.start();
	}
	
	public static void startCLSimulation(){
		
	}

	public static void main(String args[]){
		
		
		if(args.length <= 0 || args[0].equals("mode-gui")){
			startUISimulation();
			
		}else if(args[0].equals("mode-cl")){
			
			if(args.length < 7){
				System.out.println("Please specify [number-of-peers map-width map-height hostility-factor peer-speed number-of-packets].");
				return;
			}
			
			//Extract command line properties.
			int   numberOfPeers   = Integer.parseInt(args[1]);
			int   mapWidth        = Integer.parseInt(args[2]);
			int   mapHeight       = Integer.parseInt(args[3]);
			float hostilityFactor = Float.parseFloat(args[4]);
			float peerSpeed       = Float.parseFloat(args[5]);
			int  numberOfPackets  = Integer.parseInt(args[6]);
			
			if(numberOfPeers < 2){
				
				System.out.println("Please specify more than 2 peers in order " +
								   "to be able to send and receive packets. No " +
								   "packets will be sent.");
				
				numberOfPackets = 0;
			}
			
			
			GeoMap map = new GeoMap(mapWidth, mapHeight);
			
			new KiNCoL(numberOfPeers, map, peerSpeed, numberOfPackets, hostilityFactor).start();
			
		}else{
			System.out.println("Please specify [mode] [number-of-peers " +
							   "map-width map-height hostility-factor].");
		}
		
		//testStatMonitor();
	}
	
	private static void testStatMonitor(){
		
		
		PeerAddress addressA = new PeerAddress("12:00:00:00:00:00");
		PeerAddress addressB = new PeerAddress("12:00:00:00:00:01");
		PeerAddress addressC = new PeerAddress("12:00:00:00:00:02");
		
		/*
		this.type        = type;
		this.source      = source;
		this.destination = destination;
		this.node        = node;
		this.mode 		 = mode;
		*/
		
		StatPacket p1 = new StatPacket(PacketType.DATA,
									   addressA, 
									   addressB,
									   addressA,
									   ForwardMode.GREEDY);
		
		StatPacket p2 = new StatPacket(PacketType.DATA,
									   addressA, 
									   addressB,
									   addressB,
									   ForwardMode.GREEDY);
		StatMonitor monitor = new MasterStatMonitor();
		
		monitor.packetSent(p1);
		monitor.packetReceived(p2);
		
		
		if(p1.isSource() 
				   && p1.getSource().equals(addressA)
				   && p1.getDestination().equals(addressB)){
			System.out.println("p1 is a sent packet");
		}
		
		System.out.println("p2 is sink - " + p2.isSink() + " is source - " + p2.isSource());
		System.out.println("p1 is sink - " + p1.isSink() + " is source - " + p1.isSource());
		System.out.println(monitor.getStats().getStatResults(addressA, addressB));
	}
	
	private static void dataPacketSendingTest(){
		
		System.out.println("Starting Simulation");

		PeerAddress addressA = new PeerAddress("12:00:00:00:00:00");
		GeoLocation locationA = new GeoLocation(2000.0, 2000.0, 1.0f);

		PeerAddress addressB = new PeerAddress("12:00:00:00:00:01");
		GeoLocation locationB = new GeoLocation(0.0, 0.0, 1.0f);

		final Peer peerA = new Peer(addressA, locationA, new GeoVelocity(0f, 0f));
		final PeerAgent agentA = new PeerAgent(peerA);
		agentA.startServices();

		final Peer peerB = new Peer(addressB, locationB, new GeoVelocity(0f, 0f));
		final PeerAgent agentB = new PeerAgent(peerB);
		agentB.startServices();



		new Thread(){
			public void run(){


				byte[] payload = {0x0, 0x1, 0x2, 0x3, 0x4};

				Packet packet1 = new DataPacket(agentA, peerB, QualityOfService.CONTROL, payload);
				Packet packet2 = new DataPacket(agentA, peerB, QualityOfService.COMMUNICATION, payload);
				Packet packet3 = new DataPacket(agentA, peerB, QualityOfService.REGULAR, payload);


				for(int i=0; i<1; i++)
				{

					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					agentA.sendDataPacket((DataPacket)packet3);
					agentA.sendDataPacket((DataPacket)packet2);
					agentA.sendDataPacket((DataPacket)packet1);

				}

			}
		}.start();

	}
	private static void dataPacketTest(PeerAgent agentA, Peer peerB) {
		
		byte[] payload = {0x0, 0x1, 0x2, 0x3, 0x4};
		
		Packet packet = new DataPacket(agentA, peerB, payload);
		
		System.out.println("Data Packet after construction.");
		System.out.println(packet);
		
		Parcel parcel = packet.toParcel();
		
		System.out.println("PACKET PARCEL: \t " + parcel);
	
		System.out.println("Data Packet after being parsed from parcel.");
		System.out.println(new DataPacket(parcel));
		
	}

	public static void parcelTest(){
		
		Parcel parcel = new Parcel(16);
		
		System.out.println(parcel);
		int a = 32;
		int b = 64;
		
		parcel.add(a);
		parcel.add(b);
		
		parcel.rewind();
		
		System.out.println(parcel.getInt());
		System.out.println(parcel.getInt());

	}
	
	public static void packetTest(PeerAgent agent){
		Packet packet = new BeaconPacket(agent);
		
		System.out.println(packet);
		
		Parcel parcel = packet.toParcel();
		
		System.out.println("PACKET PARCEL: \t " + parcel);
	
		System.out.println("--PARSED PACKET--");
		System.out.println(new BeaconPacket(parcel));
		
	}
	
	public static void geoLocationTest(){
		
		GeoLocation location1 = new GeoLocation(1.0, 2.0, 3.0f);		
		GeoLocation location2 = new GeoLocation(4.0, 5.0, 6.0f);
		
		System.out.println(location2);
		location2.setLocation(location1.toParcel());
		System.out.println(location2);
	}
	
	public static void peerAddressTest(){
		
		PeerAddress address = new PeerAddress("12:00:00:00:00:00");
		
		System.out.println(address);
	}
	
	public static void connectionTest(PortConfiguration conf){
		
		final int serverPort = conf.getDataSendingServicePort();
		final int clientPort = conf.getDataReceivingServicePort();
		
		final String serverAddress = conf.getAddress();
		final String clientAddress = conf.getAddress();
	
		
		Thread server = new Thread(){
			public void run(){
				
				Connection server = new MulticastConnection(serverPort, serverAddress);
				
				if(!server.connect()){
					System.out.println("Server socket could not connect!");
					return;
				}
				
				while(true){
					
					try {
						
						GeoLocation location = new GeoLocation(1.0, 2.0, 3.0f);		
						
						System.out.println("Sending data...");
						server.send(location.toParcel().toByteArray(), clientPort,serverAddress);
						System.out.println("Sent data!");
						
						sleep(1000);
				
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
						
					}
					
					
				}
				
			}
		};
		
		Thread client = new Thread(){
			
			public void run(){
				
			
				Connection client = new MulticastConnection(clientPort, clientAddress);
			
				client.connect();
				client.setBlocking(true);
				byte[] buffer;
				while(true){
				
					try{
						
						System.out.println("Receiving data...");
						buffer = client.receive();
						
						//Check to see if the data was received.
						if(buffer != null){
							System.out.println("Data Received! \t" + new GeoLocation(new Parcel(buffer)));
						}else{
							System.out.println("Data not received!");
						}
						
						sleep(1000);
						
					}catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				
				}
				
				
				
				
			}
		};
		
	server.start();
	client.start();
		
		
		
	}
}
