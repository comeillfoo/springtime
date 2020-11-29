package org.comrades.springtime.servise;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.module.RoleEnum;
import org.comrades.springtime.module.User;

import java.util.List;


public interface UserService {

    User findUserByToken(String token) throws UserNotFoundException;
    List<User> findUserByRole(RoleEnum role) throws UserNotFoundException;
    User createUser(String login, String password, String token);
    void saveUser(User user);
}
