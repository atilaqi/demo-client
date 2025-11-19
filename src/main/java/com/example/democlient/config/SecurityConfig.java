package com.example.democlient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults()) // uses spring.security.oauth2.client config
                .logout(logout -> logout
                        .logoutSuccessUrl("http://localhost:9000/logout?client_id=my-client-id&post_logout_redirect_uri=http://localhost:8081/")
                        .invalidateHttpSession(true)
                        .deleteCookies("DEMO_CLIENT_SESSION")
                );
//        .defaultSuccessUrl("/profile", true)  // 👈 always go to /profile

        return http.build();
    }
}