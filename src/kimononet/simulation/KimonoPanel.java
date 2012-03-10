package kimononet.simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import kimononet.geo.GeoLocation;
import kimononet.geo.GeoVelocity;
import kimononet.peer.Peer;
import kimononet.peer.PeerAgent;

public class KimonoPanel extends JPanel {

	private BufferedImage m_imageUAV;
	private Simulation m_simulation;

	public KimonoPanel(BufferedImage imageUAV, Simulation simulation) {
		m_imageUAV = imageUAV;
		m_simulation = simulation;
	}

	protected void paintComponent(Graphics g) {
		// TODO: Bearing and longitude/latitude.
		AffineTransform affineTransform = new AffineTransform();
		Graphics2D g2d = (Graphics2D)g;
		ArrayList<PeerAgent> peerAgents = m_simulation.getPeerAgents();
		for (int i = 0; i < peerAgents.size(); i++) {
			Peer peer = peerAgents.get(i).getPeer();
			GeoLocation location = peer.getLocation();
			GeoVelocity velocity = peer.getVelocity();
			double longitude = 0d;
			double latitude = 0d;
			if (location != null) {
				longitude = location.getLongitude();
				latitude = location.getLatitude();
			}
			affineTransform.rotate(Math.toRadians(peer.getVelocity().getBearing()), m_imageUAV.getWidth() / 2, m_imageUAV.getHeight() / 2);
			affineTransform.translate(longitude, latitude);
			g2d.drawImage(m_imageUAV, affineTransform, this);
		}
	}

}
