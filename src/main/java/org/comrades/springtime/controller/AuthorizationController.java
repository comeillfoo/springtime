package org.comrades.springtime.controller;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.module.User;
import org.comrades.springtime.module.requested.AuthenticationRequestDto;
import org.comrades.springtime.security.jwt.TokenHandler;
import org.comrades.springtime.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NonUniqueResultException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/aunt/**")
public class AuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final TokenHandler jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthorizationController(AuthenticationManager authenticationManager, TokenHandler jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        Map<Object, Object> response = new HashMap<>();
        try {
            String username = authenticationRequestDto.getUsername();

            try {
                userService.findByUsername(username);
                throw new NonUniqueResultException("Username is already in use.");
            }catch (UserNotFoundException ex) {}

            User user = new User(username, authenticationRequestDto.getPassword());

            String refreshToken = jwtTokenProvider.generateRefreshToken(user);
            user.setRefreshToken(refreshToken);

            String accessToken = jwtTokenProvider.generateAccessToken(user);

            userService.saveUser(user);

            Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            response.put("username", username);
            response.put("refresh_token", refreshToken);
            response.put("token", accessToken);

            return ResponseEntity.ok(response);
        }catch (IncorrectResultSizeDataAccessException | NonUniqueResultException ex) {
            response.put("description", ex.getMessage());

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @PostMapping("/sign_in")
    public ResponseEntity signIn(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        Map<Object, Object> response = new HashMap<>();
        try {
            String username = authenticationRequestDto.getUsername();

            User user = userService.findByUsername(username);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDto.getPassword()));

            String accessToken = jwtTokenProvider.generateAccessToken(user);

            response.put("username", username);
            response.put("refresh_token", user.getRefreshToken());
            response.put("token", accessToken);

            return ResponseEntity.ok(response);
        }catch (UserNotFoundException | AuthenticationException ex) {
            response.put("description","Wrong login or password.");

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

}
