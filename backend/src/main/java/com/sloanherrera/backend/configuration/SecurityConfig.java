package com.sloanherrera.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Provides all security configuration for NetExpenseTracker backend.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Security will be addressed in-depth in Iteration 3 due to time constraints. In 
    // Iteration 1, all requests are permitted to properly test the application. By
    // default, Spring Security secures all endpoints, which is not ideal for testing
    // without advanced configuration.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
            .anyRequest().permitAll()).formLogin((form) -> form.disable())
            .httpBasic((basic) -> basic.disable());
        return http.build();
    }

    // Configure BCryptPasswordEncoder as a Spring Bean for password hashing.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
