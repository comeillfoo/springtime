package org.comrades.springtime.servise;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.module.User;


public interface UserService {

    User findUserByToken(String token) throws UserNotFoundException;
    User createUser(String login, String password, String token);
    void saveUser(User user);
}
