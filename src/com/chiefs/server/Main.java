package com.chiefs.server;

import java.io.IOException;
import java.net.SocketException;

public class Main {

	private static final int port = 1337; 
	
	public static void main(String[] args) {
		Forme forme = new Forme(150);
		try {
			Server server = new Server(port, forme);
			server.startListening();
		} catch (SocketException e) {
			System.err.println("Could not open server at specified port");
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println("Server error: ");
			e.printStackTrace();
		}
	}

}
