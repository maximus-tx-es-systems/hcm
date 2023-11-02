package com.maximus.hcm.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.maximus.hcm.service.HealthCheckService;


@Service
public class HealthCheckScheduler extends BaseQCProcessor{

	public static Logger logger = LoggerFactory.getLogger(BaseQCProcessor.class);
	public static final String PROCESSOR_NAME = "health-check-processor";
	private HealthCheckService service;
	
	@Autowired
	public HealthCheckScheduler(HealthCheckService service){
		super(PROCESSOR_NAME);
		this.service = service;
	}
	
	@Scheduled(fixedRate = 300000, initialDelay = 0)
	public void Schedule(){
		super.run();
	}

	@Override
	public void process() {
		logger.info("HealthCheckScheduler.process()>>>>>>>>>>>>>>>>>>>>>>>>> Execution DateTime:{}", new Date());
		try {
			service.monitorTask();
		}catch(Exception ex) {
			logger.error("Exception {} ", ex.getMessage(), ex);
			ex.printStackTrace();
		}
	}
	
}
