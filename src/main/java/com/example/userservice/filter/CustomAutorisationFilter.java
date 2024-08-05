package com.example.userservice.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public class CustomAutorisationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getServletPath().equals("/api/login")) {
            System.out.println("hello");
            filterChain.doFilter(request, response);
        } else {

            String autho = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (autho != null && autho.startsWith("Bearer ")) {
                System.out.println("authopp");
                try {
                    String token = autho.substring("Bearer ".length());

                    Algorithm algo = Algorithm.HMAC256("secret".getBytes());

                    JWTVerifier verifier = JWT.require(algo).build();
                    DecodedJWT decodejwt = verifier.verify(token);

                    String username = decodejwt.getSubject();
                    System.out.println(username);
                    String[] roles = decodejwt.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authori = new ArrayList<>();
                    Arrays.stream(roles).forEach(role -> authori.add(new SimpleGrantedAuthority(role)));
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authori);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    log.error("Eroor login in  {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    //response.sendError(HttpStatus.FORBIDDEN.value());
                    Map<String, String> errors = new HashMap<>();
                    errors.put("error_message", exception.getMessage());
                    //System.out.println (exception.getMessage());
                    //tokens.put("refresh_token", refresh_token);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), errors);
                }
            } else {
                filterChain.doFilter(request, response);
            }

        }

    }

}
