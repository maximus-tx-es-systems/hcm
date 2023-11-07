package com.maximus.hcm.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maximus.hcm.dto.AppConfigDTO;
import com.maximus.hcm.dto.HealthCheckDTO;
import com.maximus.hcm.dto.HealthCheckModel;
import com.maximus.hcm.dto.KeyValueDTO;
import com.maximus.hcm.dto.StatusDTO;
import com.maximus.hcm.utils.CommonUtils;
import com.maximus.hcm.utils.EmailSender;

import jakarta.mail.internet.AddressException;

@Service
@ConfigurationProperties
public class HealthCheckService {
	public static Logger logger = LoggerFactory.getLogger(HealthCheckService.class);
	private static final String APP_HEALTH_CHECK_URLS_PROP="apps.health.check.urls";
	private static final String APP_HEALTH_CHECK_APP_LIST_PROP= "apps.health.check.list";
	private static final String APP_HEALTH_CHECK_MAIN_LIST_PROP= "apps.health.check.main.appCodes";
	private static final String APP_CODE_PREFIX = "apps.health.check.appCode.";
	private static final String MAIN_APPCODE_HAS_VIP_SUFFIX = ".has-vip";
	private static final String MAIN_APPCODE_VIP_CODES_SUFFIX = ".uri-code-list.vips";
	private static final String MAIN_APPCODE_HAS_HAP_SUFFIX = ".has-haproxy";
	private static final String MAIN_APPCODE_HAP_CODES_SUFFIX = ".uri-code-list.haproxy";
	private static final String MAIN_APPCODE_DEFAULT_URI_SUFFIX = ".default-uri";
	private static final String MAIN_APPCODE_DISPLAY_NAME_SUFFIX = ".display-name";
	private static final String MAIN_APPCODE_DISABLED_URI_LIST  = ".disabled-list";
	private static final String MAIN_APPCODE_DIRECT_CODES_SUFFIX = ".uri-code-list.direct";
	private static final String DEFAULT_APP_HEALTH_CHECK_LIST_PROP = "apps.health.check.list.default";
	private static final String DEFAULT_APP_HEALTH_CHECK_MAIN_LIST = "MAXeQC,CCA,OAT,FSS";
	private static final String DEFAULT_URL ="http://maximus.com";
	private static final String HC_NOTIFICATION_EMAIL_IDS_PROP="health.check.notification.email.ids";
	private static final String DEFAULT_ADMIN_EMAILID_PROP ="default.admin.notification.email.id";
	private static final String DEFAULT_ADMIN_EMAILID = "HariKarthikSundaram@maximus.com";
	private static final String APP_CODE_PROP_PREFIX = "apps.health.check.url.";
	private static final String BLANK_BOX_PLACEHOLDER = "BLANK";
//	private static final String STACK_TRACE_TRIMMER_TOKEN = "HttpClient.java";
	private static final String APP_HEALTH_CHECK_EMAIL_FOOTER_PROP = "apps.health.check.email.footer";
	private static final String DEFAULT_HEALTH_CHECK_EMAIL_EMAIL_FOOTER = "<i>Disclaimer: This is a system generated email. "
			+ "Please do not reply to this message. For any questions or concerns regarding this email, "
			+ "then please reach out to <b>TX ES Systems</b> team at email id: <b>tx_es_systems@maximus.com</b></i>";
	private static final String COMMA = ",";
	private static final String ALERT_PREFIX = "Alert!!! ";
//	private static final int ERROR_CODE = -1;
	private static final String HTTP_GET ="GET";
	private static String hostName;
	private static final ObjectMapper mapper = new ObjectMapper();
	private static List<String> appUrlList = new ArrayList<String>();
	private static final ArrayList<String> recepients = new ArrayList<String>();
	private Environment environment;
	
	private static List<String> appList = new ArrayList<>();
	private static List<String> mainAppList = new ArrayList<>();
	private static List<AppConfigDTO> mainAppConfigList = new ArrayList<>();
	private static Map<String, HealthCheckModel> statusMap = new HashMap<>();
	private static List<HealthCheckModel> unhealthyHealthCheckModelList = new ArrayList<>();
//	private static WebClient webClient = WebClient.create();
	
	@Value("${healthcheck.apps.list}")
	private String[] appsList;
	
	private boolean notifyByEmail;
	
	private LinkedHashMap<String, StatusDTO> mainAppCodeStatusMap;
	
	private Map<String, AppConfigDTO> appsConfigMap;
	
	private List<String> appsConfigList;
	
	private Map<String, String> reverseNodeCodeMap;	
	
	private Map<String, String> errorDescAppCodeMap;
		
	@Autowired
	public void init(Environment environment) throws Exception{
		this.environment= environment;
		this.notifyByEmail = true;
		mainAppCodeStatusMap = new LinkedHashMap<>();
		appsConfigList = new ArrayList<>();
		appsConfigMap = new HashMap<>();
		reverseNodeCodeMap = new HashMap<>();
		errorDescAppCodeMap = new HashMap<>();
		
		try{
			hostName = InetAddress.getLocalHost().getCanonicalHostName();
			logger.info("host name: {}", hostName);
			CommonUtils.prepareHttpsConnection();
			parseStrCommaList(this.environment.getProperty(APP_HEALTH_CHECK_APP_LIST_PROP, 
							this.environment.getProperty(DEFAULT_APP_HEALTH_CHECK_LIST_PROP, DEFAULT_APP_HEALTH_CHECK_MAIN_LIST)), appList);
			parseStrCommaList(this.environment.getProperty(APP_HEALTH_CHECK_URLS_PROP, DEFAULT_URL), appUrlList);
			parseStrCommaList(this.environment.getProperty(HC_NOTIFICATION_EMAIL_IDS_PROP, 
					this.environment.getProperty(DEFAULT_ADMIN_EMAILID_PROP, DEFAULT_ADMIN_EMAILID)), recepients);
			
			parseStrCommaList(this.environment.getProperty(APP_HEALTH_CHECK_MAIN_LIST_PROP, DEFAULT_APP_HEALTH_CHECK_MAIN_LIST), mainAppList);
		}catch(Exception ex){
			logger.error("Failed to obtain the host name... "
				 + "or failed to parse the comma separated list of App URL list or notification email id list... {} ", ex.getMessage(), ex);
		}
		buildAppCodeConfiguration();
	}
	
	public List<AppConfigDTO> getAllAppCheckStatus(){
//		return Arrays.asList(statusMap.values().toArray());
		return mainAppConfigList;
	}
	
	public List<String> getAllhomePageAppCodes(){
		return mainAppList;
	}
	
	public Map<String, AppConfigDTO> getAllhomePageAppConfig(){
		return appsConfigMap;
	}
	
	public LinkedHashMap<String,StatusDTO> getAllhomePageAppCodeStatus(){
		return mainAppCodeStatusMap;
	}
	
	public LinkedHashMap<String,StatusDTO> getModuleAppCodeStatus() {
		return mainAppCodeStatusMap;
	}
	
	public StatusDTO getAppCodeStatus(String appCode) throws Exception{
		AppConfigDTO appConfig = getAppConfig(appCode);
		return appConfig.getConsolidatedNodeCodeStatusMap().get(appCode);
	}
	
	public List<String> getAllhomePageAppCodeList(){
		return appsConfigList;
	}
	
	public List<HealthCheckModel> getAllNotOkStatus(){
		return unhealthyHealthCheckModelList;
	}
	
	public KeyValueDTO getEmailStatus(){
		return new KeyValueDTO("notifyByEmail", "" + notifyByEmail);
	}
	
	public KeyValueDTO getAppCodeExceptionDescription(String appCode) throws Exception{
		String detailedErrorDesc = errorDescAppCodeMap.get(appCode);
		if(detailedErrorDesc == null) {
			AppConfigDTO appConfig = getAppConfig(appCode);
			StatusDTO statusDTO = appConfig.getConsolidatedNodeCodeStatusMap().get(appCode);
			detailedErrorDesc = statusDTO.getError();
		}
		return new KeyValueDTO("detailErrorDesc", detailedErrorDesc);
	}
	
	public KeyValueDTO getAppCodeEmailStatus(String appCode) throws Exception{
		AppConfigDTO appConfig = getAppConfig(appCode);
		StatusDTO statusDTO = appConfig.getConsolidatedNodeCodeStatusMap().get(appCode);
		if(statusDTO!=null) {
			statusDTO.setNotifyByEmail(statusDTO.isNotifyByEmail()?true:false);
		}
		return new KeyValueDTO("notifyByEmail", "" + statusDTO.isNotifyByEmail());
	}
	
	public StatusDTO updateEmailStatus(String appCode, KeyValueDTO keyValueDTO) throws Exception{
		AppConfigDTO appConfig = getAppConfig(appCode);
		StatusDTO statusDTO = appConfig.getConsolidatedNodeCodeStatusMap().get(appCode);
		if(statusDTO!=null && keyValueDTO!=null && "notifyByEmail".equalsIgnoreCase(keyValueDTO.getKey())) {
			statusDTO.setNotifyByEmail("true".equalsIgnoreCase(keyValueDTO.getValue())?true:false);
		}else {
			throw new Exception("Invalid Payload");
		}
		return getAppCodeStatus(appCode);
	}
	
	public StatusDTO updateActiveStatus(String appCode, KeyValueDTO keyValueDTO) throws Exception{
		AppConfigDTO appConfig = getAppConfig(appCode);
		StatusDTO statusDTO = appConfig.getConsolidatedNodeCodeStatusMap().get(appCode);
		if(statusDTO!=null && keyValueDTO!=null && "active".equalsIgnoreCase(keyValueDTO.getKey())) {
			statusDTO.setActive("true".equalsIgnoreCase(keyValueDTO.getValue())?true:false);
		}else {
			throw new Exception("Invalid Payload");
		}
		
		if(appConfig.getDefaultNodeCode().equalsIgnoreCase(appCode)) {
			appConfig.setActive(statusDTO.isActive());
		}
		
		return getAppCodeStatus(appCode);
	}
	
	public KeyValueDTO updateEmailStatus(KeyValueDTO statusDTO){
		if(statusDTO!=null && "notifyByEmail".equalsIgnoreCase(statusDTO.getKey())) {
			updateNotificationByEmailStatus("true".equalsIgnoreCase(statusDTO.getValue())?true:false);
		}
		return getEmailStatus();
	}
	
	public void updateNotificationByEmailStatus(boolean notifyByEmail) {
		this.notifyByEmail = notifyByEmail;
	}
	/*
	public void monitorTask() {
		logger.info("==================================================================================================================");
//		unhealthyHealthCheckModelList.clear();
		unhealthyHealthCheckModelList = new ArrayList<>();
		for(String appCode: appList){
			String appUrl = this.environment.getProperty(APP_CODE_PROP_PREFIX + appCode);
			logger.info("Trying to read the app url for app code : {} appUrl: {}", appCode, appUrl);
			HealthCheckModel healthCheckModel = checkAppHealthStatus(appCode, appUrl);
			statusMap.put(appCode, healthCheckModel);
			if(healthCheckModel.hasError() || healthCheckModel.hasException()) {
				unhealthyHealthCheckModelList.add(healthCheckModel);
			}
		}
		//process the health check validation and update the corresponding data stores.
		processHealthCheckStatus();
		
		// notify the subscriptions with the NOT_OK app's list status
		if(notifyByEmail) {
			notifyByEmail(unhealthyHealthCheckModelList);
		}
	}
	*/
	
	public String monitorTask(String appCode) throws Exception {
		AppConfigDTO appConfig = getAppConfig(appCode);
		List<HealthCheckModel> unhealthyHealthCheckModelList = new ArrayList<>();
		logger.info("appCode={} appConfig.getDefaultAppCode()= {}", appCode, appConfig.getDefaultNodeCode());
		String appUriCode = appConfig.getDefaultNodeCode();
		String appDefaultUri = getNodeCodeUri(appUriCode);
		logger.info("Trying to read the app url for app code : {} appUrl: {}", appUriCode, appDefaultUri);
		HealthCheckModel healthCheckModel = checkAppHealthStatus(appUriCode, appDefaultUri);
		appConfig.setStatus(healthCheckModel.getStatus());
		appConfig.setStatusCode(healthCheckModel.getStatusCode());
		appConfig.setErrSummary(healthCheckModel.getErrSummary());
		appConfig.setLastCheckedOn(new Date());
		statusMap.put(appCode, healthCheckModel);
		monitorConsolidatedNodeCodes(appConfig);
		
		if (appConfig.isNotifyByEmail() && (healthCheckModel.hasError() || healthCheckModel.hasException())) {
			unhealthyHealthCheckModelList.add(healthCheckModel);
		}
		//process the health check validation and update the corresponding data stores.
		processHealthCheckStatus(appCode);

		// notify the subscriptions with the NOT_OK app's list status
		if(notifyByEmail && appConfig.isNotifyByEmail() && !unhealthyHealthCheckModelList.isEmpty()) {
			notifyByEmail(unhealthyHealthCheckModelList);
		}
		return appCode + " validation triggered successfully";
	}
	
	private AppConfigDTO getAppConfig(String appCode) throws Exception {
		AppConfigDTO appConfig = appsConfigMap.get(appCode);
		if(appConfig==null) {
			appConfig = appsConfigMap.get(reverseNodeCodeMap.get(appCode));
		}
		if(appConfig==null) {
			throw new Exception("App code " + appCode + " not found!!");
		}
		return appConfig;
	}
	
	public void monitorTask() throws Exception{
		logger.info(
				"==================================================================================================================");
		resetStatus();
		
		// unhealthyHealthCheckModelList = new ArrayList<>();
		for (String appCode : mainAppList) {
			boolean skipAppCodeCheck = false;
			AppConfigDTO appConfig = getAppConfig(appCode);
			if(appConfig!=null) {
				StatusDTO nodeStatusDTO = appConfig.getConsolidatedNodeCodeStatusMap().get(appCode);
				if(!appConfig.isActive() || (nodeStatusDTO!=null && !nodeStatusDTO.isActive())) {
					skipAppCodeCheck= true;
				}
			}
			if (BLANK_BOX_PLACEHOLDER.equalsIgnoreCase(appCode) || skipAppCodeCheck) {
				continue;
			}
		/*	AppConfigDTO appConfig = appsConfigMap.get(appCode);
			logger.info("appCode={} appConfig.getDefaultAppCode()= {}", appCode, appConfig.getDefaultAppCode());
			String appUriCode = appConfig.getDefaultAppCode();
			String appDefaultUri = getAppCodeUri(appUriCode);
			logger.info("Trying to read the app url for app code : {} appUrl: {}", appUriCode, appDefaultUri);
			HealthCheckModel healthCheckModel = checkAppHealthStatus(appUriCode, appDefaultUri);
			appConfig.setStatus(healthCheckModel.getStatus());
			appConfig.setStatusCode(healthCheckModel.getStatusCode());
			appConfig.setErrSummary(healthCheckModel.getErrSummary());
			appConfig.setLastCheckedOn(new Date());
			statusMap.put(appCode, healthCheckModel);
			monitorConsolidatedAppCodes(appConfig);
			if (appCode.equals("MAXeQC") || appCode.equals("EID")) {
				appConfig.setWarningOn("Y"); // temporarily hard coded this for testing purpose
			}
			if (healthCheckModel.hasError() || healthCheckModel.hasException()) {
				unhealthyHealthCheckModelList.add(healthCheckModel);
			}
			*/
			monitorTask(appCode);
		}
		//process the health check validation and update the corresponding data stores.
		processHealthCheckStatus();

		// notify the subscriptions with the NOT_OK app's list status
		if(notifyByEmail && !unhealthyHealthCheckModelList.isEmpty()) {
			notifyByEmail(unhealthyHealthCheckModelList);
		}
	}
	
	private void resetStatus() {
		unhealthyHealthCheckModelList.clear();
		for (String appCode : mainAppList) {
			AppConfigDTO appConfig = appsConfigMap.get(appCode);
			appConfig.setErrSummary("N/A");
			appConfig.setWarningOn("N/A");
		}
	}
	
	private void monitorConsolidatedNodeCodes(AppConfigDTO appConfig) {
		LinkedHashMap<String, StatusDTO> consolidatedNodeCodeStatusMap = appConfig.getConsolidatedNodeCodeStatusMap();
		if(consolidatedNodeCodeStatusMap!=null && !consolidatedNodeCodeStatusMap.isEmpty()) {
			for(String nodeCode: appConfig.getConsolidatedNodeCodes()) {
				StatusDTO statusDTO = consolidatedNodeCodeStatusMap.get(nodeCode);
				HealthCheckModel healthCheckModel = checkAppHealthStatus(nodeCode, statusDTO.getNodeCodeUri());
				updateAppCodeStatus(appConfig, healthCheckModel, statusDTO);
			}
		}
	}
	
	private void updateAppCodeStatus(AppConfigDTO appConfig, HealthCheckModel healthCheckModel, StatusDTO statusDTO) {
		if(!appConfig.isActive() || !statusDTO.isActive()) {
			logger.info("Ignoring the update for appCode...{} ", statusDTO.getAppCode());
			return;
		}
		statusDTO.setStatusCode(healthCheckModel.getStatusCode());
		statusDTO.setStatus(healthCheckModel.getStatus());
		errorDescAppCodeMap.put(healthCheckModel.getAppCode(), healthCheckModel.getErrDescription());
		if(appConfig.getDefaultNodeCode().equalsIgnoreCase(healthCheckModel.getAppCode())) {
			statusDTO.setError(healthCheckModel.getErrSummary());
			appConfig.setErrSummary(healthCheckModel.getErrSummary());
			appConfig.setStatus(healthCheckModel.getStatus());
			appConfig.setStatusCode(healthCheckModel.getStatusCode());
			if(!(healthCheckModel.hasError() || healthCheckModel.hasException()) 
					&& ("N/A".equalsIgnoreCase(healthCheckModel.getErrSummary()) ||
							"OK".equalsIgnoreCase(healthCheckModel.getErrSummary()))){
				statusDTO.setWarningOn("N");
				statusDTO.setError(healthCheckModel.getResponseData());
			}else {
				appConfig.setWarningOn("Y");
				unhealthyHealthCheckModelList.add(healthCheckModel);
			}
		}else {
			if(healthCheckModel.hasError() || healthCheckModel.hasException()) {
				statusDTO.setError(healthCheckModel.getErrSummary());
				appConfig.setWarningOn("Y");
				unhealthyHealthCheckModelList.add(healthCheckModel);
			}else if("OK".equalsIgnoreCase(healthCheckModel.getErrSummary())){
				statusDTO.setWarningOn("N");
				statusDTO.setError(healthCheckModel.getResponseData());
			}
		}
	}
	
	private void processHealthCheckStatus(String appCode) {
		// Iterate the main health check app code list and Read the app health check status from the data store
		AppConfigDTO appConfig = appsConfigMap.get(appCode);
		if(appConfig!=null) {
			StatusDTO statusDTO = mainAppCodeStatusMap.get(appCode);
//			if(statusDTO==null) {
//				statusDTO = new StatusDTO();
//			}
//			statusDTO.setStatusCode(appConfig.getStatusCode());
//			statusDTO.setStatus(appConfig.getStatus());
//			statusDTO.setWarningOn(appConfig.getWarningOn());
//			statusDTO.setError(appConfig.getErrSummary());
			mainAppCodeStatusMap.put(appCode, statusDTO);
		}
	}
	
	
	private void processHealthCheckStatus() {
		// Iterate the main health check app code list and Read the app health check status from the data store
		for(String appCode: mainAppList) {
			processHealthCheckStatus(appCode);
		}
	}
	
//	private LinkedHashMap<String, StatusDTO> updateAppCodeStatusCollection(AppConfigDTO appConfig){
//		LinkedHashMap<String, StatusDTO> consolidatedAppCodeStatusMap= appConfig.getConsolidatedNodeCodeStatusMap();
//		if(consolidatedAppCodeStatusMap!=null && appConfig.getConsolidatedNodeCodes()!=null && !appConfig.getConsolidatedNodeCodes().isEmpty()) {
//			for(String appCodeUri: appConfig.getConsolidatedNodeCodes()) {
//				if(appCodeUri!=null) {
//					StatusDTO statusDTO = consolidatedAppCodeStatusMap.get(appCodeUri);
//					statusDTO.setAppCodeUri(getAppCodeUri(appCodeUri));
//					consolidatedAppCodeStatusMap.put(appCodeUri, statusDTO);
//				}
//			}
//		}
//		return consolidatedAppCodeStatusMap;
//	}
	
	private void buildAppCodeConfiguration() {
		// 1. obtain the main appCode list
		for(String appCode: mainAppList) {
			AppConfigDTO appConfig = new AppConfigDTO(appCode);
			List<String> vipNodeCodes = new ArrayList<>();
			List<String> haproxyNodeCodes= new ArrayList<>();
			List<String> directNodeCodes= new ArrayList<>();
			Set<String> consolidatedNodeCodes= new HashSet<>();
//			LinkedHashMap<String, StatusDTO> consolidatedAppCodeStatusMap= new LinkedHashMap<>();
//			appConfig.setConsolidatedNodeCodeStatusMap(consolidatedAppCodeStatusMap);
			String strDisplayName= environment.getProperty(APP_CODE_PREFIX + appCode+ MAIN_APPCODE_DISPLAY_NAME_SUFFIX, appCode);
			appConfig.setDisplayName(strDisplayName);
			// read the default-uri properties file for app configs.
			String strDefaultUriCode = environment.getProperty(APP_CODE_PREFIX + appCode+ MAIN_APPCODE_DEFAULT_URI_SUFFIX);
			appConfig.setDefaultNodeCode(strDefaultUriCode);
//			consolidatedAppCodes.add(strDefaultUriCode);
			
			// read the properties file for app configs.
			String strHasVIPUrl = environment.getProperty(APP_CODE_PREFIX + appCode+ MAIN_APPCODE_HAS_VIP_SUFFIX, "false");
			if("true".equalsIgnoreCase(strHasVIPUrl)) {
				// read the vip config properties file for app configs.
				parseStrCommaList(environment.getProperty(APP_CODE_PREFIX + appCode+ MAIN_APPCODE_VIP_CODES_SUFFIX), vipNodeCodes);
				appConfig.setVipNodeCodes(vipNodeCodes);
//				appConfig.setVipNodeCodesStatusMap(buildAppCodesStatusMap(vipAppCodes));
				consolidatedNodeCodes.addAll(vipNodeCodes);
			}
						
			String strHasHAPUrl = environment.getProperty(APP_CODE_PREFIX + appCode+ MAIN_APPCODE_HAS_HAP_SUFFIX, "false");
			if("true".equalsIgnoreCase(strHasHAPUrl)) {
				// read the has-haproxy config properties file for app configs.
				parseStrCommaList(environment.getProperty(APP_CODE_PREFIX + appCode+ MAIN_APPCODE_HAP_CODES_SUFFIX), haproxyNodeCodes);
				appConfig.setHaproxyNodeCodes(haproxyNodeCodes);
//				appConfig.setHaproxyNodeCodesStatusMap(buildAppCodesStatusMap(haproxyAppCodes));
				consolidatedNodeCodes.addAll(haproxyNodeCodes);
			}
			
			// read the direct url codes properties file for app configs.
			parseStrCommaList(environment.getProperty(APP_CODE_PREFIX + appCode+ MAIN_APPCODE_DIRECT_CODES_SUFFIX), directNodeCodes);
			appConfig.setDirectNodeCodes(directNodeCodes);
			consolidatedNodeCodes.addAll(directNodeCodes);
//			appConfig.setDirectNodeCodesStatusMap(buildAppCodesStatusMap(directAppCodes));
			appConfig.setConsolidatedNodeCodes(consolidatedNodeCodes);
			appConfig.setConsolidatedNodeCodeStatusMap(buildNodeCodesStatusMap(consolidatedNodeCodes, appConfig));
			updateDisabledNodes(appConfig);
			appsConfigList.add(appCode);
			appsConfigMap.put(appCode, appConfig);
			//Store the config
			mainAppConfigList.add(appConfig);
			updateReverseAppCodeMapping(appConfig);
		}
	}
	
	private void updateDisabledNodes(AppConfigDTO appConfig) {
		List<String> disabledNodesList = new ArrayList<>();
		// read the disabled config properties file for app configs.
		parseStrCommaList(environment.getProperty(APP_CODE_PREFIX + appConfig.getAppCode()+ MAIN_APPCODE_DISABLED_URI_LIST), disabledNodesList);
		if(!disabledNodesList.isEmpty()) {
			Map<String, StatusDTO> consolidatedNodeCodeStatusMap = appConfig.getConsolidatedNodeCodeStatusMap();
			for(String nodeCode: disabledNodesList) {
				StatusDTO statusDTO = consolidatedNodeCodeStatusMap.get(nodeCode);
				if(statusDTO!=null) {
					statusDTO.setActive(false);
				}
			}
		}
	}
	
	private void updateReverseAppCodeMapping(AppConfigDTO appConfig) {
		if(appConfig==null) {
			return;
		}
		
		if(appConfig.getVipNodeCodes()!=null && !appConfig.getVipNodeCodes().isEmpty()) {
			for(String appVipCode: appConfig.getVipNodeCodes()) {
				reverseNodeCodeMap.put(appVipCode, appConfig.getAppCode());
			}
		}
		
		if(appConfig.getHaproxyNodeCodes()!=null && !appConfig.getHaproxyNodeCodes().isEmpty()) {
			for(String appHapCode: appConfig.getHaproxyNodeCodes()) {
				reverseNodeCodeMap.put(appHapCode, appConfig.getAppCode());
			}
		}
		
		if(appConfig.getDirectNodeCodes()!=null && !appConfig.getDirectNodeCodes().isEmpty()) {
			for(String appDirectCode: appConfig.getDirectNodeCodes()) {
				reverseNodeCodeMap.put(appDirectCode, appConfig.getAppCode());
			}
		}
	}
	
	private LinkedHashMap<String, StatusDTO> buildNodeCodesStatusMap(Set<String> appNodesList, AppConfigDTO appConfig){
		LinkedHashMap<String, StatusDTO> consolidatedNodeCodeStatusMap= new LinkedHashMap<>();
		if(appConfig!=null && appConfig.getDefaultNodeCode()!=null)
			consolidatedNodeCodeStatusMap.put(appConfig.getDefaultNodeCode(), buildStatusDTO(appConfig.getDefaultNodeCode(), appConfig));
		if(appNodesList!=null && !appNodesList.isEmpty()) {
			for(String appNodeCode: appNodesList) {
				if(appConfig.getDefaultNodeCode()!=null && appNodeCode.equalsIgnoreCase(appConfig.getDefaultNodeCode())) {
					continue;
				}
				if(appNodeCode!=null)
					consolidatedNodeCodeStatusMap.put(appNodeCode, buildStatusDTO(appNodeCode, appConfig));
			}
		}
		
		return consolidatedNodeCodeStatusMap;
	}

	private StatusDTO buildStatusDTO(String appNodeCode, AppConfigDTO appConfig) {
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setAppCode(appConfig.getAppCode());
		statusDTO.setActive(true);
		statusDTO.setNodeCode(appNodeCode);
		statusDTO.setNodeCodeUri(getNodeCodeUri(appNodeCode));
		return statusDTO;
	}
	
	private String getNodeCodeUri(String uriAppCode) {
		return environment.getProperty(APP_CODE_PROP_PREFIX + uriAppCode);
	}
	
	private void notifyByEmail(List<HealthCheckModel> unhealthyHealthCheckModelList) {
		if(!unhealthyHealthCheckModelList.isEmpty()) {
			//build the valid AppCode list for sending email..
			List<HealthCheckModel> emailNotificationModelList = new ArrayList<>();
			
			for(HealthCheckModel hcmodel: unhealthyHealthCheckModelList) {
				AppConfigDTO appConfig = null;
				try {
					appConfig = getAppConfig(hcmodel.getAppCode());
				}catch(Exception ex) {
					ex.printStackTrace();
					logger.error("notifyByEmail() exception while fetching appConfig...Exception: {}", ex.getMessage(), ex);
				}
				if(appConfig!=null) {
					StatusDTO statusDTO = appConfig.getConsolidatedNodeCodeStatusMap().get(hcmodel.getAppCode());
					if(appConfig.isActive() && statusDTO!=null && statusDTO.isActive()) {
						emailNotificationModelList.add(hcmodel);
					}
				}
			}
			if(!emailNotificationModelList.isEmpty()) {
				sendEmail(emailNotificationModelList);
			}else {
				logger.info("EMAIL_NO_MODEL_TO_NOTIFY :: No healthcheck notification available for emailing...");
			}
		}
	}
	
	private void parseStrCommaList(String strCommaList, List<String> dstList) {
		if(strCommaList==null) return;
		for(String str: strCommaList.split(COMMA)) {
			dstList.add(str);
		}
	}
	
	private HealthCheckModel checkAppHealthStatus(String appUriCode, String appUri) {
		HealthCheckModel healthCheckModel = new HealthCheckModel();
		healthCheckModel.setAppCode(appUriCode);
		healthCheckModel.setAppUrl(appUri);
		HttpURLConnection connection = null;
		try {
			logger.info("*************** Checking App URL \"{}\" for Health check *****************", appUri);
			URI uri = new URI(appUri);
			healthCheckModel.setRequestTime(new Date());
			connection = (HttpURLConnection) uri.toURL().openConnection();
			connection.setRequestMethod(HTTP_GET);
			int code = connection.getResponseCode();
			healthCheckModel.setResponseTime(new Date());
			healthCheckModel.setResponseData(connection.getResponseMessage());
			logger.info("App URL: \"{}\" Health check status code: {} ", appUri, code);
			if(code == HttpURLConnection.HTTP_OK){
				logger.info("{} App URL is reachable ..", appUri);
//				healthCheckModel.setStatus(""+code);
			}else{
				healthCheckModel.setHasError(true);
//				healthCheckModel.setStatus(""+code);
				healthCheckModel.setErrDescription(HttpStatus.resolve(code).name());
//				healthCheckModel.setErrSummary(HttpStatus.resolve(code).name());
				logger.error("{} App URL is NOT OK ..", appUri);
//				sendEmail(buildHealthCheckDTO(appUrl, code, buildUrlMessage(Response.Status.fromStatusCode(code).name(), appUrl)), appUrl);
			}
			healthCheckModel.setStatus(HttpStatus.valueOf(code).name());
			healthCheckModel.setStatusCode(""+code);
			healthCheckModel.setErrSummary(HttpStatus.resolve(code).name());
		} catch (Exception e) {
			healthCheckModel.setHasException(true);
			healthCheckModel.setResponseTime(new Date());
			healthCheckModel.setStatus("EXCEPTION");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			healthCheckModel.setErrDescription(sw.toString());
//			String errMsgString = sw.toString().substring(0, sw.toString().indexOf(STACK_TRACE_TRIMMER_TOKEN));
//			StackTraceElement[] elements = e.getStackTrace();
			healthCheckModel.setErrSummary(e.toString());
			logger.error("{} APP URL has Exception!! Exception messge : {} Description: {}", appUri, e.getMessage(), healthCheckModel.getErrSummary());
//			sendEmail(buildHealthCheckDTO(appUrl, ERROR_CODE, "APP URL " + appUrl + " got Exception: " + e.getMessage()), appUrl);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		return healthCheckModel;
	}
	
	private void setExceptionDetails(Exception ex, HealthCheckModel healthCheckModel) {
		healthCheckModel.setHasException(true);
		healthCheckModel.setResponseTime(new Date());
		healthCheckModel.setStatus("EXCEPTION");
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		healthCheckModel.setErrDescription(sw.toString());
		logger.info("Exception: " + ex.getCause());
		if(ex.getCause()!=null) {
			healthCheckModel.setErrSummary(ex.toString());
		}else {
			healthCheckModel.setErrSummary(ex.toString());
		}
		
	}
	
	private String buildConsolidatedEmailSubject(List<HealthCheckModel> healthCheckModelList) {
		StringBuilder builder = new StringBuilder();
		for(HealthCheckModel healthCheckModel: healthCheckModelList) {
			if(builder.toString().isBlank()) {
				builder.append(ALERT_PREFIX).append(healthCheckModel.getAppCode());
			}else {
				builder.append(COMMA).append(" ").append(healthCheckModel.getAppCode());
			}
		}
		builder.append(" NOT reachable!!!");
		
		return builder.toString();
	}
	
	private String buildConsolidatedEmailContent(List<HealthCheckModel> healthCheckModelList) {
		StringBuilder builder = new StringBuilder("<table width='95%' border='1' align='center'><tr align='center'>")
				.append("<td><b>App Code</b></td>")
				.append("<td><b>App Health Check URL</b></td>")
				.append("<td><b>Health Check Status</b></td>")
				.append("<td><b>Check Initiated Time</b></td>")
				.append("<td><b>Check Completed Time<b></td>")
				.append("<td><b>Error Description<b></td>")
				.append("</tr>");
		for(HealthCheckModel healthCheckModel: healthCheckModelList) {

			builder.append("<tr align='center'>");
			builder.append("<td>").append(healthCheckModel.getAppCode()).append("</td>");
			builder.append("<td>").append(healthCheckModel.getAppUrl()).append("</td>");
			builder.append("<td>").append(healthCheckModel.getStatus()).append("</td>");
			builder.append("<td>").append(healthCheckModel.getRequestTime()).append("</td>");
			builder.append("<td>").append(healthCheckModel.getResponseTime()).append("</td>");
			builder.append("<td>").append(healthCheckModel.getErrSummary()).append("</td>");
			builder.append("</tr>");

			//			builder.append("---------------------------------------------------------------------------------------------------\n")
			//			.append(String.format("%20s | %50s | %50s |%50s | %50s | %100s", 
			//					healthCheckModel.getAppCode(),
			//					healthCheckModel.getAppUrl(),
			//					healthCheckModel.getStatus(),
			//					healthCheckModel.getRequestTime(), 
			//					healthCheckModel.getResponseTime(),
			//					healthCheckModel.getErrDescription())).append("\n");
			//			builder.append("---------------------------------------------------------------------------------------------------\n");

		}
		builder.append("</table>");
		builder.append("<br>---------------------------------------------------------------------------------------------------");
		builder.append("<br>Monitoring Host : <b>").append(hostName).append("</b>");
		builder.append("<br>---------------------------------------------------------------------------------------------------<br>");
		builder.append(environment.getProperty(APP_HEALTH_CHECK_EMAIL_FOOTER_PROP, DEFAULT_HEALTH_CHECK_EMAIL_EMAIL_FOOTER));
		return builder.toString();
	}
	
	private boolean sendEmail(List<HealthCheckModel> healthCheckModelList){
		try {
			return EmailSender.sendTextMail(recepients, 
											buildConsolidatedEmailSubject(healthCheckModelList), 
											buildConsolidatedEmailContent(healthCheckModelList));
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return false;
	} 
	
	@Deprecated
	private HealthCheckDTO buildHealthCheckDTO(String appUrl, int code, String message) {
		HealthCheckDTO healthCheckDTO = new HealthCheckDTO();
		healthCheckDTO.setStatusCode(code);
		healthCheckDTO.setStatusMessage(message);
		healthCheckDTO.setMonitoringHost(hostName);
		healthCheckDTO.setMonitoringUrl(appUrl);
		return healthCheckDTO;
	}
	
	private String buildUrlMessage(String reason, String appUrl){
		StringBuilder builder = new StringBuilder(appUrl).append(" is not reachable ***\n");
		builder.append(" Reason: ").append(reason);
		return builder.toString();
	}
	
	private String buildEmailContent(HealthCheckDTO healthCheckDTO){
		String content = null;
		try{
			content= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(healthCheckDTO);
		}catch(Exception ex){
			StringBuilder builder = new StringBuilder("Failed while building the content..healthCheckDTO:");
			builder.append(healthCheckDTO).append( "  Exception: ").append(ex.getMessage());
			content =  builder.toString() ;
		}
		
		return content;
	}
	
	private boolean sendEmail(HealthCheckDTO healthCheckDTO, String appUrl){
		try {
			StringBuilder subBuilder = new StringBuilder(ALERT_PREFIX).append(appUrl).append(" NOT reachable!!!");
			return EmailSender.sendTextMail(recepients, null, null, subBuilder.toString(), buildEmailContent(healthCheckDTO));
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return false;
	} 
	
	private static final String rawAppConfig="";
	
	
}
