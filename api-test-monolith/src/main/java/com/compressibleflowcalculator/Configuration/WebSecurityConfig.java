package com.compressibleflowcalculator.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // (1)
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // (2)// (2)
import org.springframework.security.config.Customizer;

import org.springframework.security.web.SecurityFilterChain; // (3)
// (3)

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("OPTIONS", "/**").permitAll()
                        .requestMatchers("*", "/**").permitAll()
                        .anyRequest().authenticated())
                // .requestMatchers(http.OPTIONS,"/**").permitAll()
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

}