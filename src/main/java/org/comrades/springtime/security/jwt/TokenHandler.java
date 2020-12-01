package org.comrades.springtime.security.jwt;

import io.jsonwebtoken.*;
import org.comrades.springtime.customExceptions.TokenNotProvidedException;
import org.comrades.springtime.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenHandler implements Tokenable {

    @Value("${secret.key}")
    private String secretKey = null;
    @Value("${token.expire_time}")
    private Long expireTime = null;

//    private SecretKey secretKeyClass = null;
    private String secretKeyK = null;
    private UserDetailsService userDetailsService;

    private String initAndGetSecretKey() {
        if (secretKeyK == null) {
            secretKeyK = Base64.getEncoder().encodeToString(secretKey.getBytes());
            secretKey = secretKeyK;
        }
//        if (secretKeyClass == null) {
//            secretKeyClass = new SecretKeySpec(this.secretKey.getBytes(), 0, this.secretKey.getBytes().length, "AES");
//        }



        return secretKey;
    }

    @Autowired
    public TokenHandler(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    public String generateRefreshToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getName());

//        SecretKey secretKeyClass = initAndGetSecretKey();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, initAndGetSecretKey())
//                .signWith(secretKeyClass)
                .compact();

        return refreshToken;
    }

    @Override
    public String generateAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getName());

//        SecretKey secretKeyClass = initAndGetSecretKey();

        Date dateNow = new Date();
        Date expireDate = new Date(dateNow.getTime() + expireTime);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(dateNow)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, initAndGetSecretKey())
//                .signWith(secretKeyClass)
                .compact();

        return accessToken;
    }


    @Override
    public String resolveToken(HttpServletRequest req) throws TokenNotProvidedException {
        String tokenShell = req.getHeader("Authorization");
        if (tokenShell != null && tokenShell.startsWith("shell_")) {
            return tokenShell.substring(6);
        }
        throw new TokenNotProvidedException("Token not found in headers.");
    }

    @Override
    public boolean validateToken(String token) {
        try {
//            SecretKey secretKeyClass = initAndGetSecretKey();

            Jws<Claims> claims = Jwts.parser().setSigningKey(initAndGetSecretKey()).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
    }



    @Override
    public String getUsername(String token) {
//        SecretKey secretKeyClass = initAndGetSecretKey();
        //TODO: check if it's work
//        return Jwts.parserBuilder().setSigningKey(secretKeyClass.toString()).requireSubject(token).toString();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
