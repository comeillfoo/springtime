package org.comrades.springtime.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

@Component
public class TokenHandler implements Tokenable {

    @Value("${secret.key}")
    private String secretKey = null;

    private UserDetailsService userDetailsService;

    @Autowired
    public TokenHandler(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String generateRefreshToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getName());

        SecretKey secretKey = new SecretKeySpec(this.secretKey.getBytes(), 0, this.secretKey.getBytes().length, SignatureAlgorithm.HS256.toString());

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();

        return token;
    }

    @Override
    public String resolveToken(HttpServletRequest req) throws TokenNotProvidedException {
        String tokenShell = req.getHeader("Authorization");
        if (tokenShell != null && tokenShell.startsWith("shell_")) {
            return tokenShell.substring(6);
        }
        throw new TokenNotProvidedException("Token not found in headers.");
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
    }


    @Override
    public String getUsername(String token) {
        //TODO: check if it's work
        return Jwts.parserBuilder().setSigningKey(secretKey).requireSubject(token).toString();
//        parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
