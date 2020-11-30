package org.comrades.springtime.dao;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.module.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByToken(String token) throws UserNotFoundException;
    User findUserByLogin(String login) throws UserNotFoundException;
//    List<User> findUserByRole(Role role);
}
