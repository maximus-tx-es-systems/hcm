package com.maximus.hcm.config;

import org.apache.coyote.http2.Http2Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.maximus.hcm.servlet.ConfigServlet;

@Configuration
@EnableScheduling
@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.maximus.hcm")
public class ApplicationConfig {

	private Environment environment;
	private static final String CORS_PATH_PATTERN_PROP = "cors.action.ctrl.allow.origin";
	private static final String DEFAULT_CORS_PATH_PATTERN = "http://localhost:3000,http://localhost:8080,https://prd-mxieqct-vip.hhscie.txaccess.net";

	@Autowired
	public ApplicationConfig(Environment environment) {
		super();
		this.environment = environment;
	}

	@Bean public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
				new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setProperties(System.getProperties());
		propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		return propertySourcesPlaceholderConfigurer; }
/*
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins(environment.getProperty(CORS_PATH_PATTERN_PROP, DEFAULT_CORS_PATH_PATTERN))
				.allowedMethods("*")
				.allowedHeaders("*")
				.exposedHeaders("*")
				.allowCredentials(false);
			}
		};
	}

	@Bean
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins(environment.getProperty(CORS_PATH_PATTERN_PROP, DEFAULT_CORS_PATH_PATTERN))
		.allowedMethods("*")
		.allowedHeaders("*")
		.exposedHeaders("*")
		.allowCredentials(false);

		registry.addMapping("/**")
		.allowedOrigins("*")
		.allowedMethods("*")
		.allowedHeaders("*")
		.exposedHeaders("*")
		.allowCredentials(true);
	}
*/
	@Bean 
	public ServletRegistrationBean<ConfigServlet> customServletBean() {
		ServletRegistrationBean<ConfigServlet> bean = new ServletRegistrationBean<ConfigServlet>(
				new ConfigServlet(this.environment), "/config"); 
		return bean; 
	}

	@Bean
	public ConfigurableServletWebServerFactory tomcatCustomizer() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(connector -> connector.addUpgradeProtocol(new Http2Protocol()));
		return factory;
	}

}
