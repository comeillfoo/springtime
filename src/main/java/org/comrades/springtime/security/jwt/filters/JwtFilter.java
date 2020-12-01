package org.comrades.springtime.security.jwt.filters;


import org.comrades.springtime.customExceptions.TokenNotProvidedException;
import org.comrades.springtime.security.jwt.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private TokenHandler tokenHandler;

    @Autowired
    public JwtFilter(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = null;

        try {
            token = tokenHandler.resolveToken((HttpServletRequest) servletRequest);
        }catch (TokenNotProvidedException ex) {
            //TODO: log TokenNotProvidedException , think about actions
        }

        if (token != null && tokenHandler.validateToken(token)) {
            Authentication auth = tokenHandler.getAuthentication(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
