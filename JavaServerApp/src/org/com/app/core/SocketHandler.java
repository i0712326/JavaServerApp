package org.com.app.core;

import java.net.Socket;

public class SocketHandler {
	private static ThreadLocal<Socket> socketLocals = new ThreadLocal<Socket>();
	private static ThreadLocal<String> iinLocals = new ThreadLocal<String>();
	public static void setSocket(Socket socket){
		socketLocals.set(socket);
	}
	public static Socket getSocket(){
		return socketLocals.get();
	}
	public static void setIin(String iin){
		iinLocals.set(iin);
	}
	public static String getIin(){
		return iinLocals.get();
	}
}
