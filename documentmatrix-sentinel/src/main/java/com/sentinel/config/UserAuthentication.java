/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.config;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * @author Vipin Kumar
 * @created 16-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class UserAuthentication implements Authentication
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( UserAuthentication.class );


    private final User user;
    private boolean authenticated = true;


    public UserAuthentication( User user )
    {
        this.user = user;
    }


    /* (non-Javadoc)
     * @see java.security.Principal#getName()
     */
    @Override
    public String getName()
    {
        LOG.trace( "Method: getName called." );

        return user.getUsername();

    }


    /* (non-Javadoc)
     * @see org.springframework.security.core.Authentication#getAuthorities()
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        LOG.trace( "Method: getAuthorities called." );

        return user.getAuthorities();

    }


    /* (non-Javadoc)
     * @see org.springframework.security.core.Authentication#getCredentials()
     */
    @Override
    public Object getCredentials()
    {
        LOG.trace( "Method: getCredentials called." );

        return user.getPassword();

    }


    /* (non-Javadoc)
     * @see org.springframework.security.core.Authentication#getDetails()
     */
    @Override
    public User getDetails()
    {
        LOG.trace( "Method: getDetails called." );

        return user;

    }


    /* (non-Javadoc)
     * @see org.springframework.security.core.Authentication#getPrincipal()
     */
    @Override
    public Object getPrincipal()
    {
        LOG.trace( "Method: getPrincipal called." );

        return user.getUsername();

    }


    /* (non-Javadoc)
     * @see org.springframework.security.core.Authentication#isAuthenticated()
     */
    @Override
    public boolean isAuthenticated()
    {
        LOG.trace( "Method: isAuthenticated called." );

        return authenticated;

    }


    /* (non-Javadoc)
     * @see org.springframework.security.core.Authentication#setAuthenticated(boolean)
     */
    @Override
    public void setAuthenticated( boolean authenticated ) throws IllegalArgumentException
    {
        LOG.trace( "Method: setAuthenticated called." );
        this.authenticated = authenticated;
        LOG.trace( "Method: setAuthenticated finished." );
    }
}
