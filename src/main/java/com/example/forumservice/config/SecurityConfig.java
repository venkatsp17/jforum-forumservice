package com.example.forumservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean(name = "SecurityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disables CSRF protection
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // Ensures all requests require authentication
            )
            .formLogin(withDefaults()); // Use form login authentication method (remove httpBasic if not needed)

        return http.build();
    }
}
