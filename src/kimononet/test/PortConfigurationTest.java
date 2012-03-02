package kimononet.test;

import static org.junit.Assert.*;
import kimononet.net.p2p.port.PortConfiguration;

import org.junit.Before;
import org.junit.Test;

public class PortConfigurationTest {

	private PortConfiguration portConfiguration;

	@Before
	public void testPortConfiguration() {
		portConfiguration = new PortConfiguration(123, 456, 789);
	}

	@Test
	public void testGetBeaconServicePort() {
		assertEquals(123, portConfiguration.getBeaconServicePort());
	}

	@Test
	public void testGetDataSendingServicePort() {
		assertEquals(456, portConfiguration.getDataSendingServicePort());
	}

	@Test
	public void testGetDataReceivingServicePort() {
		assertEquals(789, portConfiguration.getDataReceivingServicePort());
	}

}
