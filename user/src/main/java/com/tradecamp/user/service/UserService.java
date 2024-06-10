package com.tradecamp.user.service;

import com.tradecamp.models.dto.UserDtoGet;
import com.tradecamp.models.exception.ResourceNotFound;
import com.tradecamp.models.model.User;
import com.tradecamp.user.repository.UserRepository;
import com.tradecamp.user.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User find(UserDtoGet userDtoGet) {
        return userRepository.findByName(userDtoGet.getName())
                .orElseThrow(() -> new ResourceNotFound(Messages.RESOURCE_NOT_FOUND.getMessage()));
    }


    public void delete(UserDtoGet userDtoGet) {
         userRepository.deleteById(find(userDtoGet).getId());
    }
}
