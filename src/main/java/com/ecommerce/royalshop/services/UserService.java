package com.ecommerce.royalshop.services;

import com.ecommerce.royalshop.models.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);
    List<User> findAllUsers();
    void DeleteUserById(Long id);
    User createUser(User user);
    User updateUser(User user);
}
