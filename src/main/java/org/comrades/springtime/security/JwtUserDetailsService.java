package org.comrades.springtime.security;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.dao.UserRepository;
import org.comrades.springtime.module.User;
import org.comrades.springtime.security.jwt.JwtFactory;
import org.comrades.springtime.security.jwt.JwtUser;
import org.comrades.springtime.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private JwtFactory<JwtUser, User> jwtUserUserJwtFactory;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        jwtUserUserJwtFactory = new JwtUserFactory();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findUserByName(s);
        } catch (UserNotFoundException e) {
            //TODO: log UserNotFoundException
        }

        if (user == null) {
            throw  new UsernameNotFoundException("User " + s + " not found!");
        }

        JwtUser jwtUser = jwtUserUserJwtFactory.create(user);

        return jwtUser;
    }
}
