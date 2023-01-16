package io.github.thatusualguy.chessonline.mygrpc;

import android.content.SharedPreferences;

import io.github.thatusualguy.chessonline.ui.MainActivity;

public class GrpcServerInfo {
	private static final String SERVER_ADDRESS = "SERVER_ADDRESS";
	private static final String SERVER_PORT = "SERVER_PORT";
	private static final String APP_NAME = "io.github.thathusualhuy.chessonline";

//	public static final String DEFAULT_ADDRESS = "10.0.2.2";
	public static final String DEFAULT_ADDRESS = "185.248.101.68";
	public static final int DEFAULT_PORT = 7105;

	public final String address;
	public final int port;

	public GrpcServerInfo(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public static GrpcServerInfo getServer() {
		SharedPreferences prefs = MainActivity.preferences;
		String address = prefs.getString(SERVER_ADDRESS, DEFAULT_ADDRESS);
		int port = prefs.getInt(SERVER_PORT, DEFAULT_PORT);

		return new GrpcServerInfo(address, port);
	}

	public static void saveServer(GrpcServerInfo server) {
		SharedPreferences prefs = MainActivity.preferences;
		prefs
				.edit()
				.putString(SERVER_ADDRESS, server.address)
				.putInt(SERVER_PORT, server.port)
				.apply();
	}
}
