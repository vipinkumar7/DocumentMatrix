/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.sentinel.service.UserProfileService;


/**
 * @author Vipin Kumar
 * @created 28-Jan-2016
 * 
 *          TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class SentinelAuthenticationProvider implements AuthenticationProvider
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( SentinelAuthenticationProvider.class );

    private UserProfileService userProfileService;


    @Autowired
    public SentinelAuthenticationProvider( UserProfileService userProfileService )
    {
        this.userProfileService = userProfileService;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate( Authentication authentication ) throws AuthenticationException
    {
        LOG.trace( "Method: authenticate called." );
        /*
         * UserProfileService profile =
         * userProfileService.findByEmail(authentication
         * .getPrincipal().toString());
         * 
         * if(profile == null){ throw new
         * UsernameNotFoundException(String.format("Invalid credentials",
         * authentication.getPrincipal())); }
         * 
         * String suppliedPasswordHash =
         * DigestUtils.shaHex(authentication.getCredentials().toString());
         * 
         * if(!profile.getPasswordHash().equals(suppliedPasswordHash)){ throw
         * new BadCredentialsException("Invalid credentials"); }
         * 
         * UsernamePasswordAuthenticationToken token = new
         * UsernamePasswordAuthenticationToken(profile, null,
         * profile.getAuthorities());
         * 
         * return token;
         */
        return null;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.authentication.AuthenticationProvider#supports
     * (java.lang.Class)
     */
    @Override
    public boolean supports( Class<?> aClass )
    {
        LOG.trace( "Method: supports called." );

        return aClass.equals( UsernamePasswordAuthenticationToken.class );

    }
}
