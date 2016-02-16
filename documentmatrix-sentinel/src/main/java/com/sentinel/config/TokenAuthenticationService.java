/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import com.sentinel.service.UserProfileService;


/**
 * @author Vipin Kumar
 * @created 16-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class TokenAuthenticationService
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( TokenAuthenticationService.class );

    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    private final TokenHandler tokenHandler;


    public TokenAuthenticationService( String secret, UserProfileService userService )
    {
        tokenHandler = new TokenHandler( secret, userService );
    }


    public String addAuthentication( HttpServletResponse response, UserAuthentication authentication )
    {
        LOG.debug( "adding auth to request" );
        final User user = authentication.getDetails();
        String token = tokenHandler.createTokenForUser( user );
        response.addHeader( AUTH_HEADER_NAME, token );
        return token;
    }


    public Authentication getAuthentication( HttpServletRequest request )
    {
        final String token = request.getHeader( AUTH_HEADER_NAME );
        if ( token != null ) {
            final User user = tokenHandler.parseUserFromToken( token );
            if ( user != null ) {
                return new UserAuthentication( user );
            }
        }
        return null;
    }

}
