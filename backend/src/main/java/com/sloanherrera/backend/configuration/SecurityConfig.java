package com.sloanherrera.backend.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Provides all security configuration for NetExpenseTracker backend.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // HTTP security and authentication configuration.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Enable CORS to allow frontend to communicate with backend.
        http.cors(Customizer.withDefaults())

            // Enable CSRF protection for sessions.
            .csrf(csrf -> csrf.csrfTokenRepository(
                CookieCsrfTokenRepository.withHttpOnlyFalse()))

            // Define which endpoints are public and protected.
            .authorizeHttpRequests(auth -> auth

                // Enable OPTIONS HTTP method on all endpoints for CORS preflight 
                // request.
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Public endpoints (Do not require authentication).
                .requestMatchers("/register", "/login").permitAll()

                // Protected endpoints (Require authentication).
                .requestMatchers("/expenses/**", "/logout").authenticated()

                // Any other endpoints require authentication.
                .anyRequest().authenticated())
            
            // Disable default form login and basic authentication
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());
                
        return http.build();
    }

    // Backend and frontend have different origins. CORS configuration is required to
    // allow frontend to send requests with session cookies.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Allow cross-origin resource sharing for frontend URL.
        corsConfig.setAllowedOrigins(List.of("http://localhost:5173"));

        // Set permitted HTTP methods. OPTIONS is included to ensure CORS works.
        corsConfig.setAllowedMethods(List.of("POST", "GET", "PUT", "DELETE", "OPTIONS"));

        // Set permitted JSON request headers. Content-Type is the default request
        // header and X-CSRF-TOKEN ensures CSRF protection is enabled.
        corsConfig.setAllowedHeaders(List.of("Content-Type", "X-CSRF-TOKEN"));

        // Allow session cookies.
        corsConfig.setAllowCredentials(true);

        // Apply CORS configuration to all endpoints.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }

    // Configure BCryptPasswordEncoder as a Spring Bean for password hashing.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
