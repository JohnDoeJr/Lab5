package com.chiefs.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
	
	private final int port;
	private final DatagramSocket server;
	private final int length = 256;
	
	private Forme forme;
	private byte[] buf = new byte[length];
	
	public Server(int port, Forme forme) throws SocketException {
		this.port = port;
		this.forme = forme;
		server = new DatagramSocket(port);
	}
	
	public void startListening() throws IOException {
		while (3 < 5) {
			DatagramPacket packet = new DatagramPacket(buf, length);
			server.receive(packet);
			Client client = new Client(packet, forme);
			client.start();
		}
	}
}
