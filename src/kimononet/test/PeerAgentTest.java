package kimononet.test;

import static org.junit.Assert.*;
import kimononet.geo.DefaultGeoDevice;
import kimononet.peer.Peer;
import kimononet.peer.PeerAgent;
import kimononet.peer.PeerEnvironment;

import org.junit.Before;
import org.junit.Test;

public class PeerAgentTest {

	private Peer peer = new Peer("01:23:45:67:89:AB");
	private PeerEnvironment environment = new PeerEnvironment();
	private DefaultGeoDevice geoDevice = new DefaultGeoDevice();

	private PeerAgent agentFromPeer;
	private PeerAgent agentFromPeerAndPeerEnvironment;
	private PeerAgent agentFromPeerAndPeerEnvironmentAndGeoDevice;

	@Before
	public void test_PeerAgent_Peer() {
		agentFromPeer = new PeerAgent(peer);
	}

	@Before
	public void test_PeerAgent_PeerPeerEnvironment() {
		agentFromPeerAndPeerEnvironment = new PeerAgent(peer, environment);
	}

	@Before
	public void test_PeerAgent_PeerPeerEnvironmentGeoDevice() {
		agentFromPeerAndPeerEnvironmentAndGeoDevice = new PeerAgent(peer, environment, geoDevice);
	}

	@Test
	public void test_getPeer() {
		assertEquals(agentFromPeer.getPeer(), peer);
		assertEquals(agentFromPeerAndPeerEnvironment.getPeer(), peer);
		assertEquals(agentFromPeerAndPeerEnvironmentAndGeoDevice.getPeer(), peer);
	}

	@Test
	public void test_getEnvironment() {
		//assertEquals(agentFromPeer.getEnvironment(), environment);
		assertEquals(agentFromPeerAndPeerEnvironment.getEnvironment(), environment);
		assertEquals(agentFromPeerAndPeerEnvironmentAndGeoDevice.getEnvironment(), environment);
	}

	@Test
	public void test_getGeoDevice() {
		//assertEquals(agentFromPeer.getGeoDevice(), geoDevice);
		//assertEquals(agentFromPeerAndPeerEnvironment.getGeoDevice(), geoDevice);
		assertEquals(agentFromPeerAndPeerEnvironmentAndGeoDevice.getGeoDevice(), geoDevice);
	}

	@Test
	public void test_setEnvironment() {
		PeerEnvironment environment2 = new PeerEnvironment();
		environment2.set("key", "value");

		agentFromPeer.setEnvironment(environment2);
		agentFromPeerAndPeerEnvironment.setEnvironment(environment2);
		agentFromPeerAndPeerEnvironmentAndGeoDevice.setEnvironment(environment2);

		assertEquals(agentFromPeer.getEnvironment(), environment2);
		assertEquals(agentFromPeerAndPeerEnvironment.getEnvironment(), environment2);
		assertEquals(agentFromPeerAndPeerEnvironmentAndGeoDevice.getEnvironment(), environment2);
	}

}
