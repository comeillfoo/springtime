package org.comrades.springtime.servise;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.module.Role;
import org.comrades.springtime.module.User;


public interface UserService {

    User findUserByToken(String token) throws UserNotFoundException;
    User findUserByLogin(String login) throws UserNotFoundException;
//    List<User> findUserByRole(Role role) throws UserNotFoundException;
    User createUser(String login, String password, Role role, String token);
    void saveUser(User user);
}
