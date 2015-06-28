package org.com.app.core.rsrc;

import java.net.Socket;
import java.util.HashMap;

public class ApplicationSockets {
	private static HashMap<String, Socket> socketHash = new HashMap<String, Socket>();
	public static HashMap<String, Socket>getSocketHash(){
		return socketHash;
	}
}
