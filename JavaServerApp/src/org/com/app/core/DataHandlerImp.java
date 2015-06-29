package org.com.app.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.com.app.core.jpos.MsgUtil;
import org.com.app.core.jpos.PackagerFactory;
import org.com.app.core.rsrc.proc.QueueProcessor;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("dataHandler")
public class DataHandlerImp implements DataHandler{
	private Logger logger = Logger.getLogger(getClass());
	private static ISOPackager packager = PackagerFactory.getPackager();
	private static final int BUFFSIZE = 4;
	private Socket socket;
	@Autowired
	private QueueProcessor queueProcessor;
	public void setSocket(Socket socket){
		this.socket = socket;
	}
	public void setQueueProcessor(QueueProcessor queueProcessor){
		this.queueProcessor = queueProcessor;
	}
	@Override
	public void handle() {
		try{
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			byte[] buffer = new byte[BUFFSIZE];
			dis.read(buffer);
			int len = Integer.parseInt(new String(buffer));
			byte[] data = new byte[len];
			if(len>0){
				dis.readFully(data);
				ISOMsg isoMsg = new ISOMsg();
				isoMsg.setPackager(packager);
				isoMsg.unpack(data);
				String mti = isoMsg.getMTI();
				logger.debug(">> Received Data Came from Port : "+socket.getLocalPort());
				MsgUtil.printLogger(isoMsg);
				if(!mti.equals("0800")){
					queueProcessor.pull(data);
					return;
				}
				isoMsg.setMTI("0810");
				isoMsg.set(39, "00");
				logger.debug("<< Send Data to Agent at Port : "+socket.getLocalPort());
				MsgUtil.printLogger(isoMsg);
				
				byte[] msg = MsgUtil.appendHeader(isoMsg.pack());
				dos.write(msg);
			}
		}
		catch(IOException ioException){
			logger.debug("IO Exception occured", ioException);
		}
		catch(InterruptedException interuptedException){
			logger.debug("Interupted Exception occured", interuptedException);
		}
		catch(ISOException isoException){
			logger.debug("ISO Exception occured",isoException);
		}
	}
	public byte[] readSocketData() {
		// TODO Auto-generated method stub
		return null;
	}
	public byte[] readQueueData() {
		// TODO Auto-generated method stub
		return null;
	}
	public void writeData(byte[] socketData) {
		// TODO Auto-generated method stub
		
	}

}
