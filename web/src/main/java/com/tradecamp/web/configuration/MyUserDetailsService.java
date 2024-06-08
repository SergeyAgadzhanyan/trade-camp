package com.tradecamp.web.configuration;

import com.tradecamp.web.dto.UserDto;
import com.tradecamp.web.dto.UserDtoGet;
import com.tradecamp.web.service.UserService;
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
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto userDto = userService.find(UserDtoGet.builder()
                .name(username)
                .build());
        return new MyUserPrincipal(userDto);

//        return new MyUserPrincipal(new User(null, "user1", encoder.encode("p1")));
    }
}
