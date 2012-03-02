package kimononet.test;

import static org.junit.Assert.*;
import kimononet.geo.DefaultGeoDevice;
import kimononet.net.p2p.port.PortConfiguration;
import kimononet.net.p2p.port.SimulationPortConfigurationProvider;
import kimononet.peer.Peer;
import kimononet.peer.PeerAgent;
import kimononet.peer.PeerEnvironment;

import org.junit.Before;
import org.junit.Test;

public class PeerAgentTest {

	private Peer peer = new Peer("01:23:45:67:89:AB");
	private PeerEnvironment environment = new PeerEnvironment();
	private DefaultGeoDevice geoDevice = new DefaultGeoDevice();
	private SimulationPortConfigurationProvider portConfigurationProvider = new SimulationPortConfigurationProvider();

	private PeerAgent peerAgentPeer;
	private PeerAgent peerAgentPeerPeerEnvironment;
	private PeerAgent peerAgentPeerPeerEnvironmentGeoDevice;
	private PeerAgent peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider;

	public PeerAgentTest() {
		testPeerAgentPeer();
		testPeerAgentPeerPeerEnvironment();
		testPeerAgentPeerPeerEnvironmentGeoDevice();
		testPeerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider();
	}

	@Before
	public void testPeerAgentPeer() {
		peerAgentPeer = new PeerAgent(peer);
	}

	@Before
	public void testPeerAgentPeerPeerEnvironment() {
		peerAgentPeerPeerEnvironment = new PeerAgent(peer, environment);
	}

	@Before
	public void testPeerAgentPeerPeerEnvironmentGeoDevice() {
		peerAgentPeerPeerEnvironmentGeoDevice = new PeerAgent(peer, environment, geoDevice);
	}

	@Before
	public void testPeerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider() {
		peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider = new PeerAgent(peer, environment, geoDevice, portConfigurationProvider);
	}

	@Test
	public void testGetPeers() {
	}

	@Test
	public void testGetPortConfiguration() {
		PortConfiguration portConfiguration = peerAgentPeer.getPortConfiguration();
		PortConfiguration portConfiguration2 = peerAgentPeerPeerEnvironment.getPortConfiguration();
		PortConfiguration portConfiguration3 = peerAgentPeerPeerEnvironmentGeoDevice.getPortConfiguration();
		PortConfiguration portConfiguration4 = peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getPortConfiguration();

		assertEquals(5000, portConfiguration.getBeaconServicePort());
		assertEquals(5001, portConfiguration.getDataSendingServicePort());
		assertEquals(5002, portConfiguration.getDataReceivingServicePort());
		assertEquals(5000, portConfiguration2.getBeaconServicePort());
		assertEquals(5001, portConfiguration2.getDataSendingServicePort());
		assertEquals(5002, portConfiguration2.getDataReceivingServicePort());
		assertEquals(5000, portConfiguration3.getBeaconServicePort());
		assertEquals(5001, portConfiguration3.getDataSendingServicePort());
		assertEquals(5002, portConfiguration3.getDataReceivingServicePort());
		assertEquals(5000, portConfiguration4.getBeaconServicePort());
		assertEquals(5001, portConfiguration4.getDataSendingServicePort());
		assertEquals(5002, portConfiguration4.getDataReceivingServicePort());
	}

	@Test
	public void testGetGeoDevice() {
		//assertEquals(geoDevice, peerAgentPeer.getGeoDevice());
		//assertEquals(geoDevice, peerAgentPeerPeerEnvironment.getGeoDevice());
		assertEquals(geoDevice, peerAgentPeerPeerEnvironmentGeoDevice.getGeoDevice());
		assertEquals(geoDevice, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getGeoDevice());
	}

	@Test
	public void testGetPeer() {
		assertEquals(peer, peerAgentPeer.getPeer());
		assertEquals(peer, peerAgentPeerPeerEnvironment.getPeer());
		assertEquals(peer, peerAgentPeerPeerEnvironmentGeoDevice.getPeer());
		assertEquals(peer, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getPeer());
	}

	@Test
	public void testGetEnvironment() {
		//assertEquals(environment, peerAgentPeer.getEnvironment());
		assertEquals(environment, peerAgentPeerPeerEnvironment.getEnvironment());
		assertEquals(environment, peerAgentPeerPeerEnvironmentGeoDevice.getEnvironment());
		assertEquals(environment, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getEnvironment());
	}

	@Test
	public void testSetEnvironment() {
		PeerEnvironment environment2 = new PeerEnvironment();
		environment2.set("key", "value");

		peerAgentPeer.setEnvironment(environment2);
		peerAgentPeerPeerEnvironment.setEnvironment(environment2);
		peerAgentPeerPeerEnvironmentGeoDevice.setEnvironment(environment2);
		peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.setEnvironment(environment2);

		assertEquals(environment2, peerAgentPeer.getEnvironment());
		assertEquals(environment2, peerAgentPeerPeerEnvironment.getEnvironment());
		assertEquals(environment2, peerAgentPeerPeerEnvironmentGeoDevice.getEnvironment());
		assertEquals(environment2, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getEnvironment());
	}

}
