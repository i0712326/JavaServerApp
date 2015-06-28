package org.com.app.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.com.app.core.entity.Member;
import org.com.app.core.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("applicationCore")
public class ApplicationCore{
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SwitchingServer switchingServer;
	@Autowired
	private MemberService memberService;
	@Autowired
	private DataProcessor dataProcessor;
	public void setMemberService(MemberService memberService){
		this.memberService = memberService;
	}
	public void setPrintServer(SwitchingServer switchingServer){
		this.switchingServer = switchingServer;
	}
	public void setDataProcessor(DataProcessor dataProcessor){
		this.dataProcessor = dataProcessor;
	}
	public void start() throws InterruptedException, IOException {
		List<Member> list = memberService.getMember();
		List<Thread> threads = new ArrayList<Thread>();
		int num = list.size();
		logger.debug("Start connection for port listener");
		for(int i=0;i<num;i++){
			Member member = list.get(i);
			int port = Integer.parseInt(member.getPort());
			String id = member.getIin();
			switchingServer.setPort(port);
			switchingServer.setId(id);
			Thread t = new Thread(switchingServer,"C"+id+"P"+port);
			t.start();
			Thread.sleep(1000);
			threads.add(t);
			
		}
		Thread thread = new Thread(dataProcessor,"C000000P0000");
		thread.start();
		threads.add(thread);
		for(Thread t : threads)	t.join();
	}

}
