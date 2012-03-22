package kimononet.simulation;

import java.util.ArrayList;

import kimononet.net.routing.QualityOfService;
import kimononet.net.transport.DataPacket;
import kimononet.peer.PeerAgent;

public class SimulationThread extends Thread {

	private static final int PACKET_SENDING_INTERVAL = 1000;

	private boolean bShutdown = false;
	private float hostilityFactor;
	private Simulation sim;

	public SimulationThread(Simulation s, float hf) {
		sim = s;
		hostilityFactor = hf;
	}

	public void stopSimulationThread() {
		bShutdown = false;
	}

	public void run() {
		while (!bShutdown) {
			// Find a random sender that is not the receiver for the next iteration.
			PeerAgent source;
			while ((source = sim.getRandomPeerAgent()) == sim.getReceiver()) { }
			sim.setSender(source);

			// Account for agents exploding in hostile environments.
			ArrayList<PeerAgent> agents = sim.getPeerAgents();
			for (int i = 0; i < agents.size(); i++) {
				if (sim.isSender(sim.getPeerAgentAt(i)) || sim.isReceiver(sim.getPeerAgentAt(i)))
					continue;
				if (Math.random() < hostilityFactor) {
					sim.getSimulationPanel().peerExplode(sim.getPeerAgentAt(i));
					sim.deletePeerAgentAt(i);
				}
			}
	
			// Send packet from source to destination.
			byte[] payload = new byte[] {0x01, 0x02};
			sim.getSender().sendDataPacket(new DataPacket(sim.getSender(), sim.getReceiver().getPeer(), QualityOfService.REGULAR, payload));
	
			// Sleep until the packet is delivered so we can compute the statistical information. 
			try {
				Thread.sleep(PACKET_SENDING_INTERVAL);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
	
			// Update statistics.
			sim.getStatResults().combine(sim.getStatMonitor().getStats().getStatResults(sim.getSender().getPeer().getAddress(), sim.getReceiver().getPeer().getAddress()));
			sim.getStatMonitor().getStats().reset();
		}
	}
}
