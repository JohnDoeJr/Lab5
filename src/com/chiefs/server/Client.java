package com.chiefs.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.chiefs.client.Nokta;

public class Client extends Thread {
	
	private static final int bufSize = 2048;
	
	private DatagramPacket packet;
	private Forme forme;
	
	public Client(DatagramPacket packet, Forme forme) {
		this.packet = packet;
		this.forme = forme;
	}
	
	@Override
	public void run() {
		try {
			callback();
		} catch (IOException e) {
			System.err.println("Client " + packet.getAddress() + " connection error:");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void callback() throws IOException {
		DatagramSocket client = new DatagramSocket();
		
		ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
		ObjectInputStream ois = new ObjectInputStream(bis);
		
		float x = ois.readFloat();
		float y = ois.readFloat();
		float r = ois.readFloat();
		ois.close();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(bufSize);
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		
		forme.setRadius(r);
		if (forme.test(new Nokta(x, y))) {
			oos.writeInt(1);
		} else {
			oos.writeInt(0);
		}
		oos.close();
		
		DatagramPacket outputPacket = new DatagramPacket(bos.toByteArray(), bos.size());
		outputPacket.setSocketAddress(packet.getSocketAddress());
		client.send(outputPacket);
	}
}
