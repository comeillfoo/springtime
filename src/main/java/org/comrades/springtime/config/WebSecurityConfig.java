package org.comrades.springtime.config;

import org.comrades.springtime.security.jwt.TokenHandler;
import org.comrades.springtime.security.jwt.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String LOGIN_PAGE = "/login/**";
    private final String MAIN_PAGE = "/main/**";

    private final TokenHandler jwtTokenHandler;

    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService, TokenHandler jwtTokenHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers( LOGIN_PAGE).permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilterBefore(new JwtFilter(jwtTokenHandler), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}