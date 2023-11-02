package com.maximus.hcm.resource;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maximus.hcm.dto.AppConfigDTO;
import com.maximus.hcm.dto.HealthCheckModel;
import com.maximus.hcm.dto.KeyValueDTO;
import com.maximus.hcm.dto.StatusDTO;
import com.maximus.hcm.service.HealthCheckService;

@RestController
@RequestMapping("/api/v1")	
public class HCMonitorResource {

	static Logger logger = LoggerFactory.getLogger(HCMonitorResource.class);
	
	@Autowired
	private HealthCheckService healthCheckService;
	
	@GetMapping(value= "status")
	public ResponseEntity<List<AppConfigDTO>> getAllAppsHealthCheckStatusMap(){
		logger.debug("inside health check status processing..");
		return ResponseEntity.ok(healthCheckService.getAllAppCheckStatus());
	}
	
	@GetMapping("error-status")
	public ResponseEntity<List<HealthCheckModel>> getNotOkAppsHealthCheckStatusList(){
		logger.debug("inside health check status processing..");
		return ResponseEntity.ok(healthCheckService.getAllNotOkStatus());
	}
	
	@GetMapping("email-status")
	public ResponseEntity<KeyValueDTO> getEmailStatus(){
		logger.debug("inside email check status..");
		return ResponseEntity.ok(healthCheckService.getEmailStatus());
	}
	
	@PostMapping("email-status")
	public ResponseEntity<KeyValueDTO> postEmailStatus(@RequestBody KeyValueDTO statusDTO){
		logger.info("inside POST email status update ..key={} got new value= {}" , statusDTO.getKey(), statusDTO.getValue());
		return ResponseEntity.ok(healthCheckService.updateEmailStatus(statusDTO));
	}
	
	@GetMapping(value= "hcm/apps-codes")
	public ResponseEntity<List<String>> getAllhomePageAppCodes(){
		logger.info("Returning the main Home page application codes...");
		return ResponseEntity.ok(healthCheckService.getAllhomePageAppCodes());
	}
	
	@GetMapping(value= "hcm/apps-list")
	public ResponseEntity<List<String>> getAllhomePageAppCodeList(){
		logger.info("Returning the main Home page application status...");
		return ResponseEntity.ok(healthCheckService.getAllhomePageAppCodeList());
	}
	
	@GetMapping(value= "hcm/apps-status")
	public ResponseEntity<Map<String,StatusDTO>> getAllhomePageAppCodeStatus(){
		logger.info("Returning the main Home page application status...");
		return ResponseEntity.ok(healthCheckService.getAllhomePageAppCodeStatus());
	}
	
	@GetMapping(value= "hcm/app/{appCode}/status")
	public ResponseEntity<Map<String,StatusDTO>> getModuleAppCodeStatus(@PathVariable String appCode){
		logger.info("Calling getModuleAppCodeStatus() status...{} ", appCode);
		return ResponseEntity.ok(healthCheckService.getModuleAppCodeStatus());
	}
	
	@GetMapping(value= "hcm/apps-config")
	public ResponseEntity<Map<String, AppConfigDTO>> getAllhomePageAppConfig(){
		logger.info("Returning the main Home page application codes...");
		return ResponseEntity.ok(healthCheckService.getAllhomePageAppConfig());
	}
	
	@GetMapping(value= "app/{appCode}/status")
	public ResponseEntity<StatusDTO> getAppCodeStatus(@PathVariable String appCode){
		logger.info("Calling getAppCodeStatus() status...{} ", appCode);
		try {
			return ResponseEntity.ok(healthCheckService.getAppCodeStatus(appCode));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return ResponseEntity.notFound().build(); 
	}
	
	@PostMapping("app/{appCode}/trigger-check")
	public ResponseEntity<String> manualAppCodeHealthCheckStatus(@PathVariable String appCode){
		logger.debug("inside health check status processing..");
		String strReponse = null;
		try {
			strReponse = healthCheckService.monitorTask(appCode);
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(strReponse);
	}
	
	@GetMapping("app/{appCode}/email-notification")
	public ResponseEntity<KeyValueDTO> getAppCodeEmailStatus(@PathVariable String appCode){
		logger.info("inside GET getAppCodeEmailStatus ..appCode={} " , appCode);
		try {
			return ResponseEntity.ok(healthCheckService.getAppCodeEmailStatus(appCode));
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("app/{appCode}/toggle-email-notification")
	public ResponseEntity<StatusDTO> toggleAppCodeEmailNotification(@PathVariable String appCode, @RequestBody KeyValueDTO statusDTO){
		logger.info("inside POST toggleAppCodeEmailNotification ..appCode={} " , appCode);
		try {
			return ResponseEntity.ok(healthCheckService.updateEmailStatus(appCode, statusDTO));
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("manual-check")
	public ResponseEntity<String> manualAppsHealthCheckStatus(@RequestParam(required = false, defaultValue = "false") String notifyByEmail){
		logger.info("inside manual health check processing..");
		if("true".equalsIgnoreCase(notifyByEmail)) {
			healthCheckService.updateNotificationByEmailStatus(true);
		}else {
			healthCheckService.updateNotificationByEmailStatus(false);
		}
		
		try {
			healthCheckService.monitorTask();
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok("Triggered the manual health check successfully");
	}
	
	@GetMapping("ping")
	public ResponseEntity<String> getPing(){
		logger.info("inside ping request processing..");
		return ResponseEntity.ok("pong");
	}
	
	@GetMapping("app/{appCode}/exception-description")
	public ResponseEntity<KeyValueDTO> getExceptionDescription(@PathVariable String appCode){
		logger.info("inside getExceptionDescription request processing..");
		try {
			return ResponseEntity.ok(healthCheckService.getAppCodeExceptionDescription(appCode));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}
	
}
