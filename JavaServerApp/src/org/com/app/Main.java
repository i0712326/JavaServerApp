package org.com.app;

import java.io.IOException;

import org.com.app.core.ApplicationCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ApplicationCore applicationCore = (ApplicationCore) context.getBean("applicationCore");
		applicationCore.start();
	}

}
