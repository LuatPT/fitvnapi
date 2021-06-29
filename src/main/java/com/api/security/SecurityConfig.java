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

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
@EnableSwagger2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService) // Cung cáp userservice cho spring security
				.passwordEncoder(passwordEncoder());// cung cấp password encoder
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://fitvn.herokuapp.com, http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTION"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Authorization", "Content-Type", "X-Auth-Token"));
        configuration.setExposedHeaders(Arrays.asList("X-Auth-Token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

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
	        "/v1/saveInfoVnPay/**",
	        "/v1/callMoMoApi/**"
	};
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable().authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
//		.antMatchers("/v1/exercises").hasAuthority(LoginRole.USER.getAuthority())
		.anyRequest().authenticated();
		// Thêm một lớp Filter kiểm tra jwt
		http.addFilterBefore(jwtAuthenticatonFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public Docket configure() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.api"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiEndPointsInfo());
	}
	private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("FIT REST API")
                                   .description("Spring Boot REST API")
                                   .contact(new Contact("LuatPT", "http://fitvn.herokuapp.com", "luatphamdhv@gmail.com"))
                                   .license("Tomcat 9.0")
                                   .licenseUrl("http://www.tomcat.org/licenses/LICENSE-9.0.html")
                                   .version("1.0.0")
                                   .build();
    }
}
