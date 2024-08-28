package com.thinkhumble.StockApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuration class for setting up Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Configures security settings for HTTP requests.
     *
     * @param http HttpSecurity object to configure security
     * @return SecurityFilterChain for the application
     * @throws Exception if there is an error configuring HttpSecurity
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())// Disable CSRF protection for simplicity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/quote", "/quotes").permitAll()// Public access to quote endpoints
                        .requestMatchers("/quote/{symbol}").authenticated()// All other requests require authentication
                        .anyRequest().authenticated())// Enable HTTP Basic authentication
                .httpBasic(withDefaults());

        return http.build();
    }

    /**
     * Configures an in-memory user details service with a single user.
     *
     * @return UserDetailsService for in-memory authentication
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Configures a password encoder for hashing passwords.
     *
     * @return PasswordEncoder using BCrypt hashing algorithm
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
