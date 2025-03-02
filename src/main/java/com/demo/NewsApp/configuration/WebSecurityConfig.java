package com.demo.NewsApp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf-> csrf
                        .disable()
                ).authorizeHttpRequests(authorizeRequests-> authorizeRequests
                        .requestMatchers("/register","/h2-console/**","/verifyRegistration","/sign-in")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
//                )
//                .formLogin(formLogin->formLogin
//                        .defaultSuccessUrl("/api/news",true)
//                        .permitAll()
                ).headers(headers->headers
                        .frameOptions(frameOptions-> frameOptions
                                .disable()
                        )
                ).addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
