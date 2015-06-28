package org.com.app.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.com.app.core.jpos.MsgUtil;
import org.com.app.core.jpos.PackagerFactory;
import org.com.app.core.rsrc.proc.HashProcessor;
import org.com.app.core.rsrc.proc.QueueProcessor;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("dataProcessor")
@Scope("prototype")
public class DataProcessor implements Runnable {
	private static ISOPackager packager = PackagerFactory.getPackager();
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private QueueProcessor queueProcessor;
	@Autowired
	private HashProcessor hashProcessor;
	public void setQueueProcessor(QueueProcessor queueProcessor){
		this.queueProcessor = queueProcessor;
	}
	public void setHashProcessor(HashProcessor hashProcessor){
		this.hashProcessor = hashProcessor;
	}
	@Override
	public void run() {
		logger.debug("Data Processing and Routing Thread");
		while (true) {
			try {
				if (queueProcessor.isExist()) {
					byte[] data = queueProcessor.fetch();
					ISOMsg isoMsg = new ISOMsg();
					isoMsg.setPackager(packager);
					isoMsg.unpack(data);
					logger.debug(">> Fetching Data from queue");
					MsgUtil.printLogger(isoMsg);
					String dest = hashProcessor.getDestination(isoMsg);
					Socket socket = hashProcessor.getSocket(dest);
					if(socket==null){
						String source = isoMsg.getString(32);
						socket = hashProcessor.getSocket(source);
						isoMsg.setMTI("0210");
						isoMsg.set(39, "91");
						data = MsgUtil.appendHeader(isoMsg.pack());
					}
					if(socket==null) continue;
					
					
					
					logger.debug("<< Sending Data to Client at Port "+socket.getLocalPort());
					MsgUtil.printLogger(isoMsg);
					DataOutputStream out = new DataOutputStream(
							socket.getOutputStream());
					out.write(data);
				}
				
			} catch (InterruptedException ine) {
				logger.debug(
						"Interrupted Exception occured while try to execute thread",
						ine);
			} catch (ISOException ise) {
				logger.debug(
						"ISO Exception occured while try to extract message",
						ise);
				// return code 72
			} catch (IOException ioe) {
				logger.info(
						"IO Exception occured while try to connect to the socket",
						ioe);
				// return code 91
			}

		}
	}

}
