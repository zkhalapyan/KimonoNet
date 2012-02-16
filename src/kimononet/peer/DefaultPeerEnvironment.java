package kimononet.peer;

public class DefaultPeerEnvironment extends PeerEnvironment{

	public DefaultPeerEnvironment()
	{
		set("beacon-frequency", "100");
		set("beacon-port", "54321");
		
		
	}
}
