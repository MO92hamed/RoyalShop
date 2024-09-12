package com.ecommerce.royalshop.controllers;

import com.ecommerce.royalshop.models.User;
import com.ecommerce.royalshop.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        userService.DeleteUserById(id);
        try {
            response.put("User Deleted Successfully", Boolean.TRUE);
            return response;
        }catch (Exception e){
            response.put("Failed to delete user", Boolean.FALSE);
            return response;
        }
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        User user1 = userService.findUserById(id);
        if (user1 != null){
            return userService.updateUser(user);
        }else
            throw new RuntimeException("Cannot update this user");
    }
}
