package com.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.security.jwt.JwtAuthenticatonFilter;
import com.api.service.impl.UserServiceImpl;

@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	
	@Bean
	public JwtAuthenticatonFilter jwtAuthenticatonFilter() {
		return new JwtAuthenticatonFilter();
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// get AuthenticationManager Bean
		return super.authenticationManagerBean();
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userServiceImpl) // Cung cáp userservice cho spring security
		.passwordEncoder(passwordEncoder());// cung cấp password encoder
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable() 
		.authorizeRequests()
		.antMatchers("/fitvnapi/v1/login").permitAll() 
		.antMatchers("/fitvnapi/v1/exercises").permitAll()// everyone can access
		.anyRequest().authenticated();
		// Thêm một lớp Filter kiểm tra jwt
		http.addFilterBefore(jwtAuthenticatonFilter(), UsernamePasswordAuthenticationFilter.class);
	}
		
}
