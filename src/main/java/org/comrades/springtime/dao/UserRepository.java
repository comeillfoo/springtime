package org.comrades.springtime.dao;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.module.RoleEnum;
import org.comrades.springtime.module.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByToken(String token) throws UserNotFoundException;
    List<User> findUserByRole(RoleEnum role);
}
