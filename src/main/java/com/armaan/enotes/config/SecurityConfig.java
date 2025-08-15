package com.armaan.enotes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for Postman testing
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll() // signup, login
                .requestMatchers("/admin/**").hasRole("ADMIN") // admin only
                .anyRequest().authenticated() // everything else requires login
            )
            .formLogin(form -> form
                .loginProcessingUrl("/public/auth/login") // POST login
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((req, res, auth) -> res.setStatus(200))
                .failureHandler((req, res, ex) -> res.setStatus(401))
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/public/auth/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
            );

        http.userDetailsService(userDetailsService); // link custom UserDetailsService

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
