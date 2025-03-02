package com.demo.NewsApp.configuration;

import com.demo.NewsApp.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.sql.Date;
import java.util.stream.Collectors;

import static com.demo.NewsApp.util.JwtUtil.getAuthentication;

public class JwtFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        System.out.println("authHeader"+authHeader);

        String[] publicEndpoints = {
                "/register",
                "/login",
                "/sign-in",
                "/verifyRegistration",
                "/h2-console",
                "/h2-console/**"
        };



        // Check if the request URI matches any public endpoint
        for (String endpoint : publicEndpoints) {
            if (request.getRequestURI().startsWith(endpoint)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String token = authHeader.replace("Bearer ", "").trim();
        Claims claims = JwtUtil.validateToken(token);

//        boolean isValidToken =  claims.getExpiration().after(new Date(System.currentTimeMillis()));
//        boolean isAuthenticated = JwtUtil.getAuthentication( claims);
//        if(!isValidToken || !isAuthenticated ){
//
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getWriter().write("Invalid Token");
//            return;
//        }

        if (claims == null || claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid or Expired Token");
            return;
        }

        boolean isAuthenticated = JwtUtil.getAuthentication(claims);
        if(!isAuthenticated){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Authentication Failed");
            return;
        }

        filterChain.doFilter(request , response);
    }
}
