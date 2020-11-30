package org.comrades.springtime.servise;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.module.User;

import java.util.List;


public interface UserService {

    User findByUsername(String login) throws UserNotFoundException;

    User findByUserId(Long id) throws UserNotFoundException;

    void delete(Long id) throws UserNotFoundException;

    User saveUser(User user);

    List<User> getAll();
}
