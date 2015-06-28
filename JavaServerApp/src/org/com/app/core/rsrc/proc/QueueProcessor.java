package org.com.app.core.rsrc.proc;

import java.util.concurrent.BlockingQueue;

import org.com.app.core.rsrc.ApplicationQueue;
import org.springframework.stereotype.Service;

@Service("queueProcessor")
public class QueueProcessor {
	public synchronized byte[] fetch() throws InterruptedException {
		BlockingQueue<byte[]> queue = ApplicationQueue.getQueue();
		return queue.take();
	}

	public synchronized void pull(byte[] data) throws InterruptedException {
		BlockingQueue<byte[]> queue = ApplicationQueue.getQueue();
		queue.put(data);
	}

	public synchronized boolean isExist() {
		BlockingQueue<byte[]> queue = ApplicationQueue.getQueue();
		return !queue.isEmpty();
	}
	
	
}
