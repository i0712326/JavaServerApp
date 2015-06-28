package org.com.app.core.rsrc.proc;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.com.app.core.rsrc.ApplicationSockets;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.springframework.stereotype.Service;
@Service("hashProcessor")
public class HashProcessor {
	private Logger logger = Logger.getLogger(getClass());
	public String getDestination(ISOMsg isoMsg) throws ISOException {
		String dest = null;
		String mti = isoMsg.getMTI();
		if(mti==null){
			logger.info("can not verify MTI, null value is found in MTI");
			throw new ISOException("MTI is null, can not verify request or response");
		}
		if(mti.equals("0200")||mti.equals("0420")){
			String pan = isoMsg.getString(2);
			dest = pan.substring(0, 6);
		}
		if(mti.equals("0210")||mti.equals("0430"))
			dest = isoMsg.getString(32);
		return dest;
	}
	public synchronized Socket getSocket(final String id) throws IOException {
		HashMap<String,Socket> socketHash = ApplicationSockets.getSocketHash();
		Socket socket = socketHash.get(id);
		return socket;
	}
	public synchronized void registerSocket(String id, Socket socket){
		HashMap<String,Socket> socketHash = ApplicationSockets.getSocketHash();
		Socket sck = socketHash.get(id);
		if(sck==null){
			logger.debug("Register Port : "+socket.getLocalPort()+" with Member ID : "+id);
			socketHash.put(id, socket);
			return;
		}
		logger.debug("Member ID : "+id+" has been registered in hash");
		
	}
}
