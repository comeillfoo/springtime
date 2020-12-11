package org.comrades.springtime.servise.impl;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.dao.UserRepository;
import org.comrades.springtime.module.User;
import org.comrades.springtime.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User findByUsername(String login) throws UserNotFoundException {
        User user;

        try {
            user = userRepository.findUserByName(login);
        }catch (UserNotFoundException ex) {
            //TODO: log UserNotFoundException
            throw new UserNotFoundException(ex.getMessage());
        }
        if (user == null) throw new UserNotFoundException("User not found.");
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

        if (user == null) throw new UserNotFoundException("");

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

        if (user == null) throw new UserNotFoundException("");

        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = null;

        try {
            user =userRepository.findUserByName(userName);
        }catch (UserNotFoundException ex) { /*NOPE*/}

        return user;
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
