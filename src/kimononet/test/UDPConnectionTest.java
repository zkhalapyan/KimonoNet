package kimononet.test;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import kimononet.geo.GeoLocation;
import kimononet.net.p2p.UDPConnection;

import org.junit.Test;

public class UDPConnectionTest {

	@Test
	public void test_UDPConnection() {
		final int clientPort = 43210;
		final String hostAddress = "255.255.255.255";
		final String clientAddress = "0.0.0.0";
		final GeoLocation location = new GeoLocation(1.0, 2.0, 3.0);

		Thread server = new Thread() {
			public void run() {
				UDPConnection server = new UDPConnection(5443);
				server.connect();

				while (true) {
					try {
						System.out.println("Sending data...");
						assertTrue(server.send(location.toByteArray(), clientPort, InetAddress.getByName(hostAddress)));
						System.out.println("Sent data!");
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					} catch (UnknownHostException e) {
						e.printStackTrace();
						break;
					}
				}
			}
		};

		Thread client = new Thread() {
			public void run() {
				try {
					UDPConnection client = new UDPConnection(clientPort, InetAddress.getByName(clientAddress));
					assertTrue(client.connect());
					client.setBlocking(true);

					while (true) {
						try {
							byte[] buffer = new byte[32];
							System.out.println("Receiving data...");
							assertTrue(client.receive(buffer));
							System.out.println("Data Received! \t" + new GeoLocation(buffer));
							assertArrayEquals(location.toByteArray(), buffer);
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							break;
						}
					}
				}
				catch (UnknownHostException e) {
					e.printStackTrace();
				} 
			}
		};

		server.start();
		client.start();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
