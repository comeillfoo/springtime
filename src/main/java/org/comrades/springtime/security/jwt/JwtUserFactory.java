package org.comrades.springtime.security.jwt;

import org.comrades.springtime.module.Role;
import org.comrades.springtime.module.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory implements JwtFactory<JwtUser, User> {

    @Override
    public JwtUser create(User user) {
        return new JwtUser(
                user.getUID(),
                user.getName(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.toString())
                ).collect(Collectors.toList());
    }
}
