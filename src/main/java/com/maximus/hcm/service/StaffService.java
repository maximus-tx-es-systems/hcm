package com.maximus.hcm.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maximus.hcm.dto.CommonLookUpVO;

@Service
public class StaffService {
	
	private CommonLookUpVO commonLookupVO;
	
	public Resource getStaffData() throws Exception {
		
		String fileURI = "C:\\Users\\512800\\eclipse-workspace\\tx-health-check-monitor\\src\\main\\resources\\staff-data.json";
		
		Path path = Paths.get(fileURI);
		Resource res = new UrlResource(path.toUri());
		
		return res;
//		StringBuilder builder = new StringBuilder();
//		List<String> lines = Files.readAllLines(Paths.get(fileURI), StandardCharsets.UTF_8);
//		for (String line : lines) {
//			System.out.println(line);
//			builder.append(line);
//		}
//		return builder.toString();
	}
	
	public CommonLookUpVO loadDataFromFile() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		String fileURI = "C:\\Users\\512800\\eclipse-workspace\\tx-health-check-monitor\\src\\main\\resources\\staff-data.json";
		Path file = ResourceUtils.getFile(fileURI).toPath();
	    commonLookupVO = mapper.readValue(file.toFile(), CommonLookUpVO.class);

		return commonLookupVO;
	}
	
	public CommonLookUpVO getStaffLookupVOData() {
		return commonLookupVO;
	}
}
