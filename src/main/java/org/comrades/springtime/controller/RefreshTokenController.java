package org.comrades.springtime.controller;

import org.comrades.springtime.customExceptions.UserNotFoundException;
import org.comrades.springtime.module.User;
import org.comrades.springtime.module.requested.AuthenticationRequestDto;
import org.comrades.springtime.module.requested.RefreshTokenDto;
import org.comrades.springtime.security.jwt.TokenHandler;
import org.comrades.springtime.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/refresh/**")
public class RefreshTokenController {

    private final TokenHandler tokenHandler;
    private final UserService userService;

    @Autowired
    public RefreshTokenController(TokenHandler tokenHandler, UserService userService) {
        this.tokenHandler = tokenHandler;
        this.userService = userService;
    }

    @PostMapping("/token")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        Map<Object, Object> response = new HashMap<>();

        try {
            String frontRefreshToken = refreshTokenDto.getRefreshToken();

            User user = userService.findByRefreshToken(frontRefreshToken);
            String tokenUserName = tokenHandler.getUsername(frontRefreshToken);

            if (user.getName().equals(tokenUserName)) {
                String accessToken = tokenHandler.generateAccessToken(user);

                response.put("token", accessToken);

                return ResponseEntity.ok(response);
            }

            throw new UserNotFoundException("Divergent indications");
        }catch (UserNotFoundException ex) {
            throw new BadCredentialsException("Invalid refreshToken");
        }
    }

}
