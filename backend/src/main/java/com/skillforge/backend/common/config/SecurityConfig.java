package com.skillforge.backend.common.config;

import com.skillforge.backend.common.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll()

                        // Problem APIs
                        .requestMatchers(HttpMethod.POST, "/api/v1/problems/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/problems/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/problems/**").hasRole("ADMIN")

                        // TestCase APIs
                        .requestMatchers(HttpMethod.POST, "/api/v1/problems/*/testcases").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/problems/*/testcases/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/problems/*/testcases/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/problems/*/testcases").hasRole("ADMIN")

                        // Public APIs
                        .requestMatchers(HttpMethod.GET, "/api/v1/problems/*/samples").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/problems/**").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}
