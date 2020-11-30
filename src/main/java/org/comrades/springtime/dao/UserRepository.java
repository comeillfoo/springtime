package org.comrades.springtime.dao;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.module.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String login) throws UserNotFoundException;
    User findUserByUID(Long id) throws UserNotFoundException;
}
