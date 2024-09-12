package com.ecommerce.royalshop.services.impl;

import com.ecommerce.royalshop.models.User;
import com.ecommerce.royalshop.repositories.UserRepository;
import com.ecommerce.royalshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("User with id [%s] is not found in our database", id)));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void DeleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
