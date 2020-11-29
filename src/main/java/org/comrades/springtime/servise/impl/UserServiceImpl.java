package org.comrades.springtime.servise.impl;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.dao.UserRepository;
import org.comrades.springtime.module.RoleEnum;
import org.comrades.springtime.module.User;
import org.comrades.springtime.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) { this.userRepository = userRepository; }


    @Override
    public User findUserByToken(String token) throws UserNotFoundException {
        User user = userRepository.findUserByToken(token);
        if (user == null) {
            throw new UserNotFoundException("User not found by token: " + token);
        }
        return user;
    }

    @Override
    public List<User> findUserByRole(RoleEnum role) {
        return userRepository.findUserByRole(role);
    }

    @Override
    public User createUser(String login, String password, String token) {
        User user = new User();

        user.setLogin(login);
        user.setPassword(password);
        user.setToken(token);

        saveUser(user);

        return user;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
