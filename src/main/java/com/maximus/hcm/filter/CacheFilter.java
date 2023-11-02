package com.maximus.hcm.filter;

import java.io.IOException;
import java.net.InetAddress;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Servlet Filter implementation class CacheFilter
 */
@Configuration
public class CacheFilter implements Filter {
	
	private static String hostName="localhost";
	public static Logger logger = LoggerFactory.getLogger(CacheFilter.class);
	
	public CacheFilter() {
		try{
			hostName = InetAddress.getLocalHost().getCanonicalHostName();
			logger.info("host name: {}", hostName);
		}catch(Exception ex){
			logger.error("CacheFilter Failed to obtain the host name... ", ex.getMessage(), ex);
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) resp;
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		response.setHeader("X-Powered-By", "Spring");
		response.setHeader("Node", hostName);
		chain.doFilter(req, response);
	}
}
