package org.example.carrentalbackend.security;

import org.example.carrentalbackend.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return (UserDetails) userRepository.findByUsername(username).map(CustomUserDetail::new).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
