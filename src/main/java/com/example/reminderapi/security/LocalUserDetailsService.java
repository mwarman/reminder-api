package com.example.reminderapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.reminderapi.model.User;
import com.example.reminderapi.service.UserService;

@Service
public class LocalUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public LocalUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userService.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid credentials.");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail()).password(user.getPassword())
                .roles("USER").build();

        return userDetails;
    }

}
