package com.ECPI.pontaj_application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
@Configuration
public class InMemoryUserConfig {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails admin = User.withUsername("Admin")
                .password(passwordEncoder.encode("Parola2"))
                .roles("ADMIN","VIEWER","GENERAL")
                .build();
        UserDetails viewer = User.withUsername("VIEW")
                .password(passwordEncoder.encode("12345"))
                .roles("VIEWER")
                .build();
        UserDetails general = User.withUsername("General")
                .password(passwordEncoder.encode("General"))
                .roles("VIEWER","GENERAL")
                .build();
        return new InMemoryUserDetailsManager(admin,viewer,general);
    }
}
