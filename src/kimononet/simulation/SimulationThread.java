package kimononet.simulation;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import kimononet.net.routing.QualityOfService;
import kimononet.net.transport.DataPacket;
import kimononet.peer.PeerAgent;

public class SimulationThread extends Thread {

	private static final int INITIALIZATION_DELAY = 2000;
	private static final int PACKET_SENDING_INTERVAL = 1000;

	private float hostilityFactor;
	private Simulation sim;

	public SimulationThread(Simulation s, float hf) {
		sim = s;
		hostilityFactor = hf;
	}

	public void run() {
		// Wait for beacon service to populate neighbor tables.
		try {
			sleep(INITIALIZATION_DELAY + Integer.parseInt(sim.getPeerEnvironment().get("beacon-service-timeout")));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (sim.isSimulationRunning()) {
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
				sleep(PACKET_SENDING_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
			// Update statistics.
			sim.getStatResults().combine(sim.getStatMonitor().getStats().getStatResults(sim.getSender().getPeer().getAddress(), sim.getReceiver().getPeer().getAddress()));
			sim.getStatMonitor().getStats().reset();
		}

		sim.setSender(null);
		sim.getSimulationPanel().clearExplosionPoints();
	}
}
