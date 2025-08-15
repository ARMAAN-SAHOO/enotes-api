package com.armaan.enotes.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.armaan.enotes.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.armaan.enotes.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // fallback role if somehow null
        String role = user.getRole() != null ? user.getRole().name() : "ROLE_USER";

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
