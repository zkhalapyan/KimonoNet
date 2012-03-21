package kimononet.peer;

import java.util.HashMap;

/**
 * The object stores variables specific to a peer environment that is not 
 * necessarily unique to the peer. Information such as peer name, peer address, 
 * and peer location are unique to the specific peer and should not be stored
 * in the environment. 
 * 
 * The key-value pairs are all string based and should be parsed to the specific
 * type before utilizing values in code.
 * 
 * @author Zorayr Khalapyan
 *
 */
public class PeerEnvironment {
	
	/**
	 * Stores key->value pairing of all the environment variables.
	 */
	private HashMap<String, String> map;
	
	/**
	 * Creates a new empty environment.
	 */
	public PeerEnvironment(){
		this.map = new HashMap<String, String>();
	}
	
	/**
	 * Returns 
	 * @param key The key mapped to the value to be returned.
	 * @return 
	 */
	public String get(String key){
		return map.get(key);
	}
	
	public void set(String key, String value){
		map.put(key, value);
	}

	public HashMap<String, String> getHashMap() {
		return map;
	}
}
