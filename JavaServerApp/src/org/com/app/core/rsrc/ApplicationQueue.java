package org.com.app.core.rsrc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ApplicationQueue {
	private static BlockingQueue<byte[]> queue = new ArrayBlockingQueue<byte[]>(500);
	
	public static BlockingQueue<byte[]> getQueue(){
		return queue;
	}
}
