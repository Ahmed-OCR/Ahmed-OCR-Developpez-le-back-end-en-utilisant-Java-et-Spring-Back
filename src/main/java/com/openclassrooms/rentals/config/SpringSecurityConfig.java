package com.openclassrooms.rentals.config;


import com.openclassrooms.rentals.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:security.properties")
public class SpringSecurityConfig {

	@Value("${security.post.allowed.urls}")
	private String postAllowedUrls;

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtFilter jwtFilter;

	public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtFilter jwtFilter) {
		this.customUserDetailsService = customUserDetailsService;
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers(postAllowedUrls, "/auth/login", "/rentals/images/**").permitAll() // Endpoint spécifique accessible sans authentification
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui/index.html").permitAll()
						.anyRequest().authenticated() // Tous les autres endpoints nécessitent une authentification
				)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}
}
