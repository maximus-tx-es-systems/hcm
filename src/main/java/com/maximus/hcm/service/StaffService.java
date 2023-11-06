package com.maximus.hcm.service;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maximus.hcm.dto.AppConfigDTO;
import com.maximus.hcm.dto.CommonLookUpVO;

@Service
public class StaffService {
	
	public static Logger logger = LoggerFactory.getLogger(StaffService.class);
	
	private CommonLookUpVO commonLookupVO;
	
	@Value("classpath:data/staff-data.json")
	Resource resourceFile;
	
	@Value("classpath:data/app-status.json")
	Resource appStatusResourceFile;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	private static List<AppConfigDTO> appConfigList;
	
	public Resource getStaffData() throws Exception {
		
		String fileURI = "classpath:data/staff-data.json";
		
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
		Resource resource = new ClassPathResource("staff-data.json");
	    commonLookupVO = mapper.readValue(resourceFile.getInputStream(), CommonLookUpVO.class);

		return commonLookupVO;
	}
	
	public CommonLookUpVO getStaffLookupVOData() {
		return commonLookupVO;
	}
	
	public List<AppConfigDTO> getFileStaffData() throws Exception{
		if(appConfigList!=null && !appConfigList.isEmpty()) {
			return appConfigList;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Resource resource = new ClassPathResource("app-status.json");
		List<AppConfigDTO> list = mapper.readValue(resource.getInputStream(), new TypeReference<List<AppConfigDTO>>(){});
		
		return list;
	}
	
}
