package com.demo.NewsApp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtil {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);



    public static String generateToken(String email, List<String> roles){
        System.out.println("secret key"+key);
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key)
                .compact();
    }

    public static Claims validateToken(String authHeader) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authHeader)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean getAuthentication(Claims claims) {
        try {
            List<String> roles = claims.get("roles", List.class);
            System.out.println("roles"+roles);
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new) // No need to cast if roles is List<String>
                    .collect(Collectors.toList());

            System.out.println("authorities"+authorities);

            // Create an Authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), // Username or email
                    null, // Credentials (not needed for JWT)
                    authorities // Roles or authorities
            );

            // Set the Authentication object in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
