package org.comrades.springtime.security.jwt;

import io.jsonwebtoken.*;
import org.comrades.springtime.customExceptions.TokenNotProvidedException;
import org.comrades.springtime.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenHandler implements Tokenable {

    @Value("${secret.key}")
    private String secretKey = null;
    @Value("${token.expire_time}")
    private Long expireTime = null;

    private SecretKey secretKeyClass = null;
    private UserDetailsService userDetailsService;

    private SecretKey initAndGetSecretKey() {
        if (secretKeyClass == null) {
            secretKeyClass = new SecretKeySpec(this.secretKey.getBytes(), 0, this.secretKey.getBytes().length, SignatureAlgorithm.HS256.toString());
        }

        return secretKeyClass;
    }

    @Autowired
    public TokenHandler(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String generateRefreshToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getName());

        SecretKey secretKeyClass = initAndGetSecretKey();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .signWith(secretKeyClass)
                .compact();

        return refreshToken;
    }

    @Override
    public String generateAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getName());

        SecretKey secretKeyClass = initAndGetSecretKey();

        Date dateNow = new Date();
        Date expireDate = new Date(dateNow.getTime() + expireTime);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(dateNow)
                .setExpiration(expireDate)
                .signWith(secretKeyClass)
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
            SecretKey secretKeyClass = initAndGetSecretKey();

            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKeyClass.toString()).parseClaimsJws(token);

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
        SecretKey secretKeyClass = initAndGetSecretKey();
        //TODO: check if it's work
//        return Jwts.parserBuilder().setSigningKey(secretKeyClass.toString()).requireSubject(token).toString();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
