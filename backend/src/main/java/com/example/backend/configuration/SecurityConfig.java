package com.example.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(h -> h
                        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "http://localhost:3000"))
                        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true")))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/perform_login", "/login").permitAll()
                        .anyRequest().authenticated())
//
                .formLogin(form -> form
                        .loginPage("http://localhost:3000/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("http://localhost:3000?success=true", true)
                        .failureUrl("http://localhost:3000/login?success=false")
                        .usernameParameter("user")
                        .passwordParameter("pass"));
        return http.build();
    }
}
