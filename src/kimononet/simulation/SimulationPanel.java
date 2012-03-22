package kimononet.simulation;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JPanel;

import kimononet.geo.GeoLocation;
import kimononet.geo.GeoVelocity;
import kimononet.geo.GeoMap;
import kimononet.peer.Peer;
import kimononet.peer.PeerAgent;

public class SimulationPanel extends JPanel {

	private BufferedImage imageUAV, imageUAVxplod, imageUAVdest, imageUAVsending;
	private final int EXPLOSION_POINT_LIFETIME = 10;
	private GeoMap mapDim;
	private HashMap<Point, Long> explosionPoints = new HashMap<Point, Long>();
	private int mouseX = -1, mouseY = -1;
	private long clock;
	private Simulation sim;

	private Rectangle calculatePeerRectangle(PeerAgent agent) {
		return new Rectangle((int)(longitudeToX(agent.getPeer().getLocation().getLongitude()) - (imageUAV.getWidth() / 2)), (int)(latitudeToY(agent.getPeer().getLocation().getLatitude()) - (imageUAV.getHeight() / 2)), imageUAV.getWidth(), imageUAV.getHeight());
	}

	public void incrementClock() {
		clock++;
	}

	public void peerExplode(PeerAgent agent) {
		if (agent != null)
			explosionPoints.put(new Point((int)(longitudeToX(agent.getPeer().getLocation().getLongitude())) - (imageUAV.getWidth() / 2), (int)(latitudeToY(agent.getPeer().getLocation().getLatitude())) - (imageUAV.getHeight() / 2)), clock);
	}

	public void clearExplosionPoints() {
		clock = 0;
		explosionPoints.clear();
	}

	public SimulationPanel(BufferedImage i1, BufferedImage i2, BufferedImage i3, BufferedImage i4, GeoMap m, Simulation s) {
		imageUAV = i1;
		imageUAVxplod = i2;
		imageUAVdest = i3;
		imageUAVsending = i4;
		mapDim = m;
		sim = s;

	    enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
	}

	public void processMouseMotionEvent(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_DRAGGED || e.getID() == MouseEvent.MOUSE_MOVED) {
			mouseX = e.getX();
			mouseY = e.getY();
			sim.getFrame().repaint();
		}

		if (e.getID() == MouseEvent.MOUSE_DRAGGED && !sim.isSimulationRunning()) {
			// Drag the currently selected peer.
			PeerAgent agent = sim.getCurrentPeerAgent();
			if ((agent != null) && calculatePeerRectangle(agent).contains(mouseX, mouseY)) {
				sim.updateCurrentPeerAgent(new GeoLocation(xToLongitude(mouseX), yToLatitude(mouseY), agent.getPeer().getLocation().getAccuracy()));
				sim.refresh();
			}
		}

		super.processMouseMotionEvent(e);
	}

	public void processMouseEvent(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_EXITED) {
			mouseX = -1;
			mouseY = -1;
			sim.getFrame().repaint();
		}

		if (e.getID() == MouseEvent.MOUSE_PRESSED) {
			// Select the peer.
			int i = 0;
			for (PeerAgent agent : sim.getPeerAgents()) {
				if (calculatePeerRectangle(agent).contains(mouseX, mouseY)) {
					sim.setCurrentPeerIndex(i);
					sim.refresh();
					break;
				}
				i++;
			}
		}

		super.processMouseEvent(e);
	}

	public void processMouseWheelEvent(MouseWheelEvent e) {
		if (!sim.isSimulationRunning()) {
			// Rotate the currently selected peer using the mouse wheel.
			PeerAgent agent = sim.getCurrentPeerAgent();
			if ((agent != null) && calculatePeerRectangle(agent).contains(mouseX, mouseY)) {
				sim.updateCurrentPeerAgent(agent.getPeer().getLocation(), new GeoVelocity(agent.getPeer().getVelocity().getSpeed(), agent.getPeer().getVelocity().getBearing() + (float)Math.toRadians(e.getWheelRotation())));
				sim.refresh();
			}
		}

		super.processMouseWheelEvent(e);
	}

	private double longitudeToX(double longitude) {
		double leftBound = mapDim.getUpperLeft().getLongitude();
		double rightBound = mapDim.getLowerRight().getLongitude();
		double range = rightBound - leftBound;
		double panelWidth = (double)getBounds().width;
		double scaler = panelWidth / range;
		return ((longitude - leftBound) * scaler);
	}

	private double latitudeToY(double latitude) {
		double topBound = mapDim.getUpperLeft().getLatitude();
		double bottomBound = mapDim.getLowerRight().getLatitude();
		double latitudeRange = topBound - bottomBound;
		double panelHeight = (double)getBounds().height; 
		double scaler = panelHeight / latitudeRange;
		return (-(latitude - topBound) * scaler);
	}

	private double xToLongitude(double x) {
		double leftBound = mapDim.getUpperLeft().getLongitude();
		double rightBound = mapDim.getLowerRight().getLongitude();
		double range = rightBound - leftBound;
		double panelWidth = (double)getBounds().width;
		double scaler = panelWidth / range;
		return (x / scaler + leftBound);
	}

	private double yToLatitude(double y) {
		double topBound = mapDim.getUpperLeft().getLatitude();
		double bottomBound = mapDim.getLowerRight().getLatitude();
		double latitudeRange = topBound - bottomBound;
		double panelHeight = (double)getBounds().height; 
		double scaler = panelHeight / latitudeRange;
		return (-y / scaler + topBound);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		final double GRID_INTERVAL = Math.toDegrees(Double.parseDouble(sim.getPeerEnvironment().get("max-transmission-range")) / (double)GeoLocation.EARTH_MEDIAN_RADIUS);

		g2d.setColor(Color.LIGHT_GRAY);

		// Paint vertical gridlines.
		for (double i = mapDim.getUpperLeft().getLongitude(); i <= mapDim.getLowerRight().getLongitude(); i += GRID_INTERVAL)
			g2d.draw(new Line2D.Double(longitudeToX(i), 0, longitudeToX(i), getBounds().height));

		// Paint horizontal gridlines.
		for (double i = mapDim.getLowerRight().getLatitude(); i <= mapDim.getUpperLeft().getLatitude(); i += GRID_INTERVAL)
			g2d.draw(new Line2D.Double(0, latitudeToY(i), getBounds().width, latitudeToY(i)));

		// Paint peers.
		for (PeerAgent agent : sim.getPeerAgents()) {
			BufferedImage image = imageUAV;

			if (sim.isSender(agent))
				image = imageUAVsending;
			else if (sim.isReceiver(agent))
				image = imageUAVdest;

			// These offsets are to make the center of the image as the origin.
			int offsetX = image.getWidth() / 2;
			int offsetY = image.getHeight() / 2;

			// Calculate peer position in pixels.
			int peerX = (int)(longitudeToX(agent.getPeer().getLocation().getLongitude())) - offsetX;
			int peerY = (int)(latitudeToY(agent.getPeer().getLocation().getLatitude())) - offsetY;

			// Rotate peer.
			AffineTransformOp atop = new AffineTransformOp(AffineTransform.getRotateInstance(agent.getPeer().getVelocity().getBearing(), offsetX, offsetY), AffineTransformOp.TYPE_BILINEAR);
			BufferedImage imageRotated = atop.filter(image, null);

			// Highlight currently selected peer.
			if (agent == sim.getCurrentPeerAgent()) {
				RescaleOp rop = new RescaleOp(1.2f, 15, null);
				rop.filter(imageRotated, imageRotated);
			}

			// Draw peer.
			g2d.drawImage(imageRotated, peerX, peerY, this);
		}

		// Paint exploded peers.
	    Iterator it = explosionPoints.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        Point point = (Point)pairs.getKey();
	        Long pointLifetime = (Long)pairs.getValue();
	        g2d.drawImage(imageUAVxplod, point.x, point.y, this);
	        if ((clock - pointLifetime) > EXPLOSION_POINT_LIFETIME)
	        	it.remove();	// Stop painting explosion after a while.
	    }

		// Paint tooltip.
		if (mouseX >= 0 && mouseY >= 0) {
			int tooltipX = mouseX + 10;
			int tooltipY = mouseY + 20;
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(tooltipX, tooltipY, 175, 50);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(tooltipX, tooltipY, 175, 50);
			g2d.drawString("Longitude: " + Float.toString((float)xToLongitude(mouseX)), tooltipX + 5, tooltipY + 20);
			g2d.drawString("Latitude: " + Float.toString((float)yToLatitude(mouseY)), tooltipX + 5, tooltipY + 40);
		}

	}
}
