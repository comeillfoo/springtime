package org.comrades.springtime.module.servise;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.dao.UserRepository;
import org.comrades.springtime.module.User;
import org.springframework.data.repository.CrudRepository;


public interface UserService {

    User findUserByToken(String token) throws UserNotFoundException;
    User createUser(String login, String password, String token);
    void saveUser(User user);
}
