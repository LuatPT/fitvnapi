package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MainApp extends SpringBootServletInitializer{
	
	private static final String[] AUTH_WHITELIST = {
			"/",
			"/csrf",
			"/v1/refreshtoken",
			"/v1/login",
			"/v1/register",
			"/v1/foods/**",
			"/v1/mealPlans/**",
			"/v1/getMealPlans/**",
			"/v1/exercisePlans/**",
			"/v1/userInfos/**",
	        "/swagger-resources/**",
	        "/v1/getUserInfos/**",
	        "/v1/getCaloMap/**",
	        "/swagger-ui.html",
	        "/v2/api-docs",
	        "/v1/api-docs",
	        "/webjars/**",
	        "/v1/paymentVNPay/**",
	        "/v1/saveInfoVnPay/**"
	};
	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainApp.class);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000,http://fitvn.herokuapp.com")
                .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
			}
		};
	}
}
