package com.example.userservice.security;

import com.example.userservice.filter.CustomAuthenficationFilter;
import com.example.userservice.filter.CustomAutorisationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private final UserDetailsService  userDetailService;
	private final BCryptPasswordEncoder  BCryptPasswordEncoder;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(BCryptPasswordEncoder);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		CustomAuthenficationFilter cc= new CustomAuthenficationFilter (authenticationManagerBean());
		cc.setFilterProcessesUrl("/api/login");
		http.authorizeHttpRequests().antMatchers("/api/login/**").permitAll();

		//http.authorizeHttpRequests().antMatchers(HttpMethod.GET,"/api/users/**").hasAuthority("ROLE_USER");
		//http.authorizeHttpRequests().antMatchers(HttpMethod.POST,"/api/user/save/**").hasAuthority("ROLE_SUPERADMIN");
		//http.authorizeRequests().anyRequest().authenticated();
	http.addFilter(cc);
	http.addFilterBefore(new CustomAutorisationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
	

	

}
