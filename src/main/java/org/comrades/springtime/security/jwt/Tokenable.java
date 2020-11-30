package org.comrades.springtime.security.jwt;

import org.comrades.springtime.customExceptions.TokenNotProvidedException;
import org.comrades.springtime.module.User;

import javax.servlet.http.HttpServletRequest;

public interface Tokenable {

    String generateRefreshToken(User user);

    String resolveToken(HttpServletRequest req) throws TokenNotProvidedException;

    String getUsername(String token);
}
