package com.tradecamp.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
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
                        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin",
                                "http://localhost:3000"))
                        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods",
                                "POST, PUT, GET, OPTIONS, DELETE"))
                        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true")))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/perform_login").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                //при добавлении кастомного обработчика пропадает дефолтная страница логина
//                .exceptionHandling(e -> e.authenticationEntryPoint(new MyUnAuthHandler()))
                .formLogin(form -> form
                        .loginProcessingUrl("/perform_login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(new MySuccessLoginHandler())
                        .failureHandler(new MyUnAuthHandler()));
        return http.build();
    }
}
