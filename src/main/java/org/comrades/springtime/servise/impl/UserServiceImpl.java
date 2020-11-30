package org.comrades.springtime.servise.impl;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.dao.UserRepository;
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
    public User findByUsername(String login) throws UserNotFoundException {
        User user;

        try {
            user = userRepository.findUserByName(login);
        }catch (UserNotFoundException ex) {
            //TODO: log UserNotFoundException
            throw new UserNotFoundException(ex.getMessage());
        }
        return user;
    }

    @Override
    public User findByUserId(Long id) throws UserNotFoundException {
        User user;

        try {
            user = userRepository.findUserByUID(id);
        }catch (UserNotFoundException ex) {
            //TODO: log UserNotFoundException
            throw new UserNotFoundException(ex.getMessage());
        }

        return user;
    }

    @Override
    public User findByRefreshToken(String refreshToken) throws UserNotFoundException {
        User user;

        try {
            user = userRepository.findUserByRefreshToken(refreshToken);
        }catch (UserNotFoundException ex) {
            //TODO: log UserNotFoundException
            throw new UserNotFoundException(ex.getMessage());
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) throws UserNotFoundException {
        User user;
        try {
             user = findByUserId(id);
        }catch (UserNotFoundException ex) {
            //TODO: log UserNotFoundException
            throw new UserNotFoundException(ex.getMessage());
        }

        userRepository.delete(user);
    }
}
