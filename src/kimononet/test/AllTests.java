package kimononet.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GeoLocationTest.class, GeoVelocityTest.class, PacketTest.class,
		ParcelTest.class, PeerAddressTest.class, PeerAgentTest.class,
		PeerEnvironmentTest.class, PeerTest.class, UDPConnectionTest.class })
public class AllTests {

}
