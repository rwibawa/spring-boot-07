/********************************************************************************
 * Copyright (c) 2015-2016 GE Digital. All rights reserved.                     *
 *                                                                              *
 * The copyright to the computer software herein is the property of GE Digital. *
 * The software may be used and/or copied only with the written permission of   *
 * GE Digital or in accordance with the terms and conditions stipulated in the  *
 * agreement/contract under which the software has been supplied.               *
 ********************************************************************************/

package com.example.springboot07.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@Configuration
@EnableAuthorizationServer
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http
        // .authorizeRequests().anyRequest().fullyAuthenticated().and()
        // .httpBasic().and()
        // .csrf().disable();

        http
        .antMatcher("/**")  // 1	All requests are protected by default
        .authorizeRequests()
            .antMatchers("/", "/login**", "/assets/**")
            .permitAll()    // 2	The home page and login endpoints are explicitly excluded
        .anyRequest()
            .authenticated()    // 3	All other endpoints require an authenticated user
        .and()
            .exceptionHandling() // 4	Unauthenticated users are re-directed to the home page
            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
        .and()
            .logout()
            .logoutSuccessUrl("/")
            .permitAll()
        .and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

}