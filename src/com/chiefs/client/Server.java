package com.chiefs.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Server {
	
	private static final int bufSize = 2048;
	
	private final int port;
	private final InetAddress address;
	private final DatagramSocket server;
	
	public Server(int port, String address) throws UnknownHostException, SocketException {
		this.port = port;
		this.address = InetAddress.getByName(address);
		server = new DatagramSocket();
		server.setSoTimeout(10000);
	}
	
	public boolean checkNokta(double x, double y, float radius) throws IOException, SocketTimeoutException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(bufSize);
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		
		oos.writeFloat(new Float(x));
		oos.writeFloat(new Float(y));
		oos.writeFloat(new Float(radius));
		oos.close();
		
		DatagramPacket packet = new DatagramPacket(bos.toByteArray(), bos.size());
		packet.setPort(port);
		packet.setAddress(address);
		server.send(packet);
		
		server.receive(packet);
		ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
		ObjectInputStream ois = new ObjectInputStream(bis);
		if (ois.readInt() == 1) {
			ois.close();
			return true;
		} else {
			ois.close();
			return false;
		}
	}
}
