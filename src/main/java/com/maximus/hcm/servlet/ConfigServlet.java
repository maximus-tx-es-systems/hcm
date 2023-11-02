package com.maximus.hcm.servlet;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ConfigServlet
 */
public class ConfigServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	public static Logger logger = LoggerFactory.getLogger(ConfigServlet.class);

	@Value("$api.url:/v1/api")
	private String apiUrl;
	
	@Value("$env.type:PROD")
	private String envType;
	
	private Environment environment;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	@Autowired
    public ConfigServlet(Environment environment) {
        super();
        this.environment = environment;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String apiUrl = this.environment.getProperty("api.url", "/api/v1");
		String envType =  this.environment.getProperty("env.type", "DEV");
		logger.info("apiUrl = {} envType= {} ", apiUrl, envType);
		System.out.println("ConfigServlet.doGet() ENVIRONMENT_TYPE = " + envType + " isDevEnv = " + !envType.equals("PROD") + " apiUrl= " + apiUrl);
		response.getWriter().append("{ \"isDevEnv\":" + !envType.equals("PROD") + ", \"apiUrl\":\"").append( "/" + apiUrl + "\"}");
	}

}
