package com.megacitycab.megabackend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/auth/**").permitAll()  // ✅ Public Endpoints
                        .requestMatchers("/drivers/available").permitAll()  // ✅ Allow public access
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")  // ✅ Admin Access Only
                        .requestMatchers("/billing/generate/**").hasAuthority("ROLE_ADMIN") // ✅ Admin-only billing generation
                        .requestMatchers("/billing/user/**").authenticated() // ✅ Users can view their own bills
                        .requestMatchers("/billing/all").hasAuthority("ROLE_ADMIN") // ✅ Admin can see all bills
                        .requestMatchers("/bookings/cancel/**").authenticated() // ✅ Allow users to cancel their own bookings
                        .requestMatchers("/drivers/earnings/**").hasAuthority("ROLE_ADMIN") // ✅ Only Admin can view earnings
                        .requestMatchers("/admin/reports/**").hasAuthority("ROLE_ADMIN") // ✅ Admin can access reports
                        .anyRequest().authenticated()  // ✅ All other requests require authentication
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler()) // ✅ Custom Access Denied Handler
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // ✅ Custom Access Denied Handler
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Access Denied: You are not authorized to access this resource.\"}");
        };
    }
}
