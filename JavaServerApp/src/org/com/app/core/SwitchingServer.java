package org.com.app.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.com.app.core.jpos.MsgUtil;
import org.com.app.core.jpos.PackagerFactory;
import org.com.app.core.rsrc.proc.HashProcessor;
import org.com.app.core.service.MemberService;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component("switchingServer")
@Scope("prototype")
public class SwitchingServer implements Runnable {
	private ISOPackager packager = PackagerFactory.getPackager();
	private Logger logger = Logger.getLogger(getClass());
	private int port;
	private String id;
	@Autowired
	private MemberService memberService;
	@Autowired
	private DataHandlerImp dataHandler;
	@Autowired
	private HashProcessor hashProcessor;
	public void setPort(int port){
		this.port = port;
	}
	public void setId(String id){
		this.id = id;
	}
	public void setDataHandler(DataHandlerImp dataHandler){
		this.dataHandler = dataHandler;
	}
	public void setMemberService(MemberService memberService){
		this.memberService = memberService;
	}
	public void setHashProcessor(HashProcessor hashProcessor){
		this.hashProcessor = hashProcessor;
	}
	@SuppressWarnings({ "resource" })
	@Override
	public void run() {
		
		try {
			logger.debug("Established connection for [ "+ id +" ] at port : "+port);
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				Socket socket = serverSocket.accept();
				int localPort = socket.getLocalPort();
				String iin = memberService.getMemberPort(String.valueOf(localPort));
				logger.debug("PORT NUMBER : "+localPort+", IIN : "+iin);
				// read data from socket
				hashProcessor.registerSocket(iin, socket);
				byte[] data = readData(socket);
				if(data!=null){
					ISOMsg isoMsg = new ISOMsg();
					isoMsg.setPackager(packager);
					isoMsg.unpack(data);
					MsgUtil.printLogger(isoMsg);
				}
			}
				/*
				byte[] socketData = dataHandler.readSocketData();
				if(socketData!=null)
					dataHandler.writeData(socketData);
				byte[] queueData = dataHandler.readQueueData();
				if(queueData!=null)
					dataHandler.writeData(queueData);
					*/
				
		} catch (IOException | ISOException e) {
			logger.debug("Exception occured while try to run server", e);
		}
		
	}
	
	private byte[] readData(Socket socket){
		try{
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			byte[] buffer = new byte[4];
			dis.read(buffer);
			int len = Integer.parseInt(new String(buffer));
			byte[] data = new byte[len];
			if(len>0){
				dis.readFully(data);
				return data;
			}
			return null;
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
}
