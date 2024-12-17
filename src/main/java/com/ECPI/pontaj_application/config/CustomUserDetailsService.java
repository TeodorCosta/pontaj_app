package com.ECPI.pontaj_application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private InMemoryUserDetailsManager manager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return manager.loadUserByUsername(username);
    }
}
