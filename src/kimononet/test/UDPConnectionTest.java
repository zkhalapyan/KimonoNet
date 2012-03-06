package kimononet.test;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import kimononet.geo.GeoLocation;
import kimononet.net.p2p.UDPConnection;

import org.junit.Test;

public class UDPConnectionTest {

	@Test
	public void testUDPConnection() {
		final int clientPort = 43210;
		final String hostAddress = "255.255.255.255";
		final String clientAddress = "0.0.0.0";
		final GeoLocation location = new GeoLocation(1.0, 2.0, (float)3.0);

		Thread server = new Thread() {
			public void run() {
				UDPConnection server = new UDPConnection(5443);
				server.connect();

				while (true) {
					try {
						System.out.println("Sending data...");
						assertTrue(server.send(location.toParcel().toByteArray(), clientPort, hostAddress));
						System.out.println("Sent data!");
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					} 
				}
			}
		};

		Thread client = new Thread() {
			public void run() {

					UDPConnection client = new UDPConnection(clientPort, clientAddress);
					assertTrue(client.connect());
					client.setBlocking(true);

					while (true) {
						try {
							System.out.println("Receiving data...");
							byte[] buffer = client.receive();
							assertArrayEquals(location.toParcel().toByteArray(), buffer);
							System.out.println("Data Received! \t");
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							break;
						}
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
