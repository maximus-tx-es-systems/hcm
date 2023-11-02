package com.maximus.hcm.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maximus.hcm.dto.CommonLookUpVO;
import com.maximus.hcm.service.StaffService;

@RestController
@RequestMapping("/api/v1")	
public class StaffResource {
	
	static Logger logger = LoggerFactory.getLogger(StaffResource.class);
	
	@Autowired
	StaffService service;
	
	@GetMapping(value= "staff-data")
	public ResponseEntity<Resource> getStaffData(){
		logger.debug("inside health check status processing..");
		try {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/json"))
		            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"staff-data.json\"")
		            .body(service.getStaffData());
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Exception: {}", ex.getMessage(), ex);
		}
		
		return ResponseEntity.internalServerError().build();
	}
	
	@GetMapping("load-ui-lookup-data")
	public ResponseEntity<CommonLookUpVO> loadFileData(){
		try {
		return ResponseEntity.ok(service.loadDataFromFile());
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Exception: {}", ex.getMessage(), ex);
		}
		
		return ResponseEntity.internalServerError().build();
	}
	
	@GetMapping("ui-lookup-data")
	public ResponseEntity<CommonLookUpVO> getStaffLookupVOData(){
		logger.info("getStaffLookupVOData called... ");
		try {
		return ResponseEntity.ok(service.getStaffLookupVOData());
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Exception: {}", ex.getMessage(), ex);
		}
		
		return ResponseEntity.internalServerError().build();
	}
	
}
