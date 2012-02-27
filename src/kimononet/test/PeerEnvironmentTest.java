package kimononet.test;

import static org.junit.Assert.*;
import kimononet.peer.PeerEnvironment;

import org.junit.Test;

public class PeerEnvironmentTest {

	@Test
	public void testPeerEnvironment() {
		PeerEnvironment environment = new PeerEnvironment();
		environment.set("key", "value");
		assertEquals("value", environment.get("key"));
	}

}
