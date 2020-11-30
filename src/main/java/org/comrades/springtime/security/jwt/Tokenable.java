package org.comrades.springtime.security.jwt;

import org.comrades.springtime.customExceptions.TokenNotProvidedException;
import org.comrades.springtime.module.User;

import javax.servlet.http.HttpServletRequest;

public interface Tokenable {

    String generateRefreshToken(User user);
    String generateAccessToken(User user);

    String resolveToken(HttpServletRequest req) throws TokenNotProvidedException;
    boolean validateToken(String token);

    String getUsername(String token);
}
