package com.example.backend.configuration;

import com.example.backend.model.User;
import com.example.backend.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final PasswordEncoder encoder;
    private final UserStorage storage;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = storage.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new MyUserPrincipal(user);
    }
}
