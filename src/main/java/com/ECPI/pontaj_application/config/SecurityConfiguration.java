package com.ECPI.pontaj_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();

    }
    //ADMIN VIEWER GENERAL
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/home").permitAll()
                                .requestMatchers("/vendor/**").permitAll()
                                .requestMatchers("/bootstrap/**").permitAll()
                                .requestMatchers("/styles.css").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/angajati").permitAll()
//                                .requestMatchers("/raport").hasAnyRole("ADMIN")
                                .requestMatchers("/raport").permitAll()
                )
                .exceptionHandling(handler->handler.accessDeniedPage("/access-denied"))
                .formLogin((form) -> form
                        .loginPage("/sign-up")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                );
        ;

        return http.build();
    }
}
