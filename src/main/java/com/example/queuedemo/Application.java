package com.example.queuedemo;

import com.example.queuedemo.service.MemberMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PreDestroy;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Application {



	@Autowired
	@Qualifier("threadPoolTaskExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@PreDestroy
	public void shutdownThread(){
		threadPoolTaskExecutor.shutdown();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
