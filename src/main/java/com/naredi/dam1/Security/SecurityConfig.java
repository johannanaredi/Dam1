package com.naredi.dam1.Security;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()  // Allt kräver inloggning
                )
                .httpBasic(Customizer.withDefaults())  // Aktiverar Basic Auth
                .csrf(csrf -> csrf.disable());        // Stänger av CSRF (bra vid test med Postman)

        return http.build();
    }
}
