/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.sentinel.service.UserProfileService;


/**
 * @author Vipin Kumar
 * @created 16-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class TokenHandler
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( TokenHandler.class );

    private final String secret;
    private final UserProfileService userService;


    public TokenHandler( String secret, UserProfileService userService )
    {
        this.secret = secret;
        this.userService = userService;
    }


    public User parseUserFromToken( String token )
    {
        String username = Jwts.parser().setSigningKey( secret ).parseClaimsJws( token ).getBody().getSubject();
        return userService.loadUserByUsername( username );
    }


    public String createTokenForUser( User user )
    {
        return Jwts.builder().setSubject( user.getUsername() ).signWith( SignatureAlgorithm.HS512, secret ).compact();
    }


}
