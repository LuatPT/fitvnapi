package com.api.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.api.security.jwt.JwtAuthenticatonFilter;
import com.api.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserService userService;
	
	
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
		auth.userDetailsService(userService) // Cung cáp userservice cho spring security
		.passwordEncoder(passwordEncoder());// cung cấp password encoder
	}
	
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("https://fitvn.herokuapp.com"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors()
		.and().csrf().disable()
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/v1/login").permitAll() 
		.antMatchers("/v1/register").permitAll()
		.antMatchers("/v1/foods/**").permitAll()// everyone can access
		.antMatchers("/v1/getMealPlans/**").permitAll()
		.antMatchers("/v1/mealPlans/**").permitAll()// everyone can access
		.antMatchers("/v1/exercisePlans/**").permitAll()// everyone can access
		.antMatchers("/v1/getUserInfos/**").permitAll()
		.antMatchers("/v1/userInfos/**").permitAll()
		.antMatchers("/v1/getCaloMap/**").permitAll()
//		.antMatchers("/v1/exercises").hasAuthority(LoginRole.USER.getAuthority())
		.anyRequest().authenticated();
		// Thêm một lớp Filter kiểm tra jwt
		http.addFilterBefore(jwtAuthenticatonFilter(), UsernamePasswordAuthenticationFilter.class);
	}
		
}
