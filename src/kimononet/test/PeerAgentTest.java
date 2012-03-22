package kimononet.test;

import static org.junit.Assert.*;
import kimononet.geo.DefaultGeoDevice;
import kimononet.net.p2p.port.SimulationPortConfigurationProvider;
import kimononet.peer.Peer;
import kimononet.peer.PeerAgent;
import kimononet.peer.PeerEnvironment;
import kimononet.time.SimulationTimeProvider;

import org.junit.Before;
import org.junit.Test;

public class PeerAgentTest {

	private Peer peer = new Peer("29:99:35:C1:8E:EB");
	private PeerEnvironment environment = new PeerEnvironment();
	private DefaultGeoDevice geoDevice = new DefaultGeoDevice();
	private SimulationPortConfigurationProvider portConfigurationProvider = new SimulationPortConfigurationProvider();
	private SimulationTimeProvider timeProvider = new SimulationTimeProvider(1234567890);

	private PeerAgent peerAgentPeer;
	private PeerAgent peerAgentPeerPeerEnvironment;
	private PeerAgent peerAgentPeerPeerEnvironmentGeoDevice;
	private PeerAgent peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider;
	private PeerAgent peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider;

	public PeerAgentTest() {
		testPeerAgentPeer();
		testPeerAgentPeerPeerEnvironment();
		testPeerAgentPeerPeerEnvironmentGeoDevice();
		testPeerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider();
		testPeerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider();
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

	@Before
	public void testPeerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider() {
		peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider = new PeerAgent(peer, environment, geoDevice, portConfigurationProvider, timeProvider);
	}

	@Test
	public void testGetEnvironment() {
		assertEquals(environment, peerAgentPeerPeerEnvironment.getEnvironment());
		assertEquals(environment, peerAgentPeerPeerEnvironmentGeoDevice.getEnvironment());
		assertEquals(environment, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getEnvironment());
		assertEquals(environment, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider.getEnvironment());
	}

	@Test
	public void testGetGeoDevice() {
		assertEquals(geoDevice, peerAgentPeerPeerEnvironmentGeoDevice.getGeoDevice());
		assertEquals(geoDevice, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getGeoDevice());
		assertEquals(geoDevice, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider.getGeoDevice());
	}

	@Test
	public void testGetPeer() {
		assertEquals(peer, peerAgentPeer.getPeer());
		assertEquals(peer, peerAgentPeerPeerEnvironment.getPeer());
		assertEquals(peer, peerAgentPeerPeerEnvironmentGeoDevice.getPeer());
		assertEquals(peer, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getPeer());
		assertEquals(peer, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider.getPeer());
	}

	@Test
	public void testGetPeers() {
		peerAgentPeer.getPeers();
		peerAgentPeerPeerEnvironment.getPeers();
		peerAgentPeerPeerEnvironmentGeoDevice.getPeers();
		peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getPeers();
		peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider.getPeers();
	}

	@Test
	public void testGetPeers2() {
		peerAgentPeer.getPeers2();
		peerAgentPeerPeerEnvironment.getPeers2();
		peerAgentPeerPeerEnvironmentGeoDevice.getPeers2();
		peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getPeers2();
		peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider.getPeers2();
	}

	@Test
	public void testGetPortConfiguration() {
		peerAgentPeer.getPortConfiguration();
		peerAgentPeerPeerEnvironment.getPortConfiguration();
		peerAgentPeerPeerEnvironmentGeoDevice.getPortConfiguration();
		peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProvider.getPortConfiguration();
		peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider.getPortConfiguration();
	}

	@Test
	public void testGetTime() {
		assertEquals(1234567890, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider.getTime());
	}

	@Test
	public void testGetTimeProvider() {
		assertEquals(timeProvider, peerAgentPeerPeerEnvironmentGeoDevicePortConfigurationProviderTimeProvider.getTimeProvider());
	}

}
