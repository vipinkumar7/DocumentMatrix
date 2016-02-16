/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentinel.service.UserProfileService;
import com.sentinel.web.dto.UserDto;


/**
 * @author Vipin Kumar
 * @created 17-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( StatelessLoginFilter.class );

    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserProfileService userProfileService;


    /**
     * @param requiresAuthenticationRequestMatcher
     */
    protected StatelessLoginFilter( String urlMapping, TokenAuthenticationService tokenAuthenticationService,
        UserProfileService userProfileService, AuthenticationManager authManager )
    {
        super( new AntPathRequestMatcher( urlMapping ) );
        this.userProfileService = userProfileService;
        this.tokenAuthenticationService = tokenAuthenticationService;
        setAuthenticationManager( authManager );
    }


    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response )
        throws AuthenticationException, IOException, ServletException
    {
        LOG.trace( "Method: attemptAuthentication called." );
        final UserDto user = new ObjectMapper().readValue( request.getInputStream(), UserDto.class );
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken( user.getEmail(),
            user.getPassword() );
        return getAuthenticationManager().authenticate( loginToken );
    }


    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response, FilterChain chain,
        Authentication authentication ) throws IOException, ServletException
    {
        // Lookup the complete User object from the database and create an Authentication for it
        final org.springframework.security.core.userdetails.User authenticatedUser = userProfileService
            .loadUserByUsername( authentication.getName() );
        final UserAuthentication userAuthentication = new UserAuthentication( authenticatedUser );
        // Add the custom token as HTTP header to the response
        tokenAuthenticationService.addAuthentication( response, userAuthentication );
        // Add the authentication to the Security context
        SecurityContextHolder.getContext().setAuthentication( userAuthentication );
    }

}
