package com.bridgingcode.springbootactivemqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootActiveMqDemoApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(SpringBootActiveMqDemoApplication.class, args);

		//we pass the application context to this static class
		Context.setApplicationContext(applicationContext);



	}

}
