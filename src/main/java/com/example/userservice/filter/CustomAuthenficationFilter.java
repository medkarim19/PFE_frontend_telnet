package com.example.userservice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
public class CustomAuthenficationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManger ;

	public CustomAuthenficationFilter(AuthenticationManager authenticationManger) {
		super();
		this.authenticationManger = authenticationManger;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		log.info("Username is : {}",username);
		log.info("Password is : {}",password);
		UsernamePasswordAuthenticationToken auth= new UsernamePasswordAuthenticationToken(username,password);
		return authenticationManger.authenticate(auth);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
											Authentication authResult) throws IOException, ServletException {
		User user=  (User)authResult.getPrincipal();
		Algorithm algorithme= Algorithm.HMAC256("secret".getBytes());
		String jwtToken= JWT.create().
				withSubject(user.getUsername()).
				withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000)).
				withIssuer(request.getRequestURL().toString())
				.withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithme);
		String refresh_token= JWT.create().
				withSubject(user.getUsername()).
				withExpiresAt(new Date(System.currentTimeMillis()+30*60*1000)).
				withIssuer(request.getRequestURL().toString())

				.sign(algorithme);
				/*response.setHeader("acess_token", jwtToken);
				response.setHeader("refresh_token", refresh_token);*/
		Map <String,String>tokens=new HashMap<>();


		tokens.put("acess_token", jwtToken);
		tokens.put("refresh_token", refresh_token);

		tokens.put("id",String.valueOf(user.toString()) );
		System.out.print("user auth"+user.getAuthorities());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);





	}

}