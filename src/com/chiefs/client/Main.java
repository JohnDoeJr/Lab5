package com.chiefs.client;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.chiefs.server.Forme;

public class Main {

	private static final int defaultRadius = 150;

	public static void main(String[] args) {

		if (args.length < 4) {
			System.err.println("Usage: java Main hostname port language country");
		} else {
			Forme forme = new Forme(defaultRadius);
			Locale locale = new Locale(args[2], args[3]);
			locale = new Locale("el", "GR");
			ResourceBundle errorsBundle = ResourceBundle.getBundle("ConnectionErrorsBundle", locale); 
			try {
				Server server = new Server(Integer.valueOf(args[1]), args[0]);
				Window window = new Window(forme, server, locale);
				window.setVisible(true);
			} catch (UnknownHostException e) {
				System.err.println(errorsBundle.getString("hostErr"));
				System.err.println(e.getMessage());
			} catch (SocketException e) {
				System.err.println(errorsBundle.getString("sockErr"));
				System.err.println(e.getMessage());
			}
		}
	}
}
