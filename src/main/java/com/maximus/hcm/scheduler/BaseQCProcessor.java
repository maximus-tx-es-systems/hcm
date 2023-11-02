package com.maximus.hcm.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseQCProcessor {

	public static Logger logger = LoggerFactory.getLogger(BaseQCProcessor.class);
	private final String processorName;
	private ExecutorService executorService;
	
	public static final String CRON_EXTN = ".cron";
	
	public BaseQCProcessor(String processorName){
		this.processorName = processorName;
	}
	
	@PostConstruct
	public void initProcessor(){
		executorService = Executors.newFixedThreadPool(10); 
	}
	
	@PreDestroy
	public void destroyProcessor(){
		if(executorService!=null){
			executorService.shutdown();
		}
	}
	
	public abstract void process();
	
	public void run(){
		logger.info("BaseQCProcessor.run() executing processor : " + this.processorName);
		process();
	}
}
