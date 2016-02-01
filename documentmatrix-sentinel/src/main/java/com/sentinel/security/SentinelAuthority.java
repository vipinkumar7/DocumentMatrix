/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licenced under Beer License
 */
package com.sentinel.security;

import org.springframework.security.core.GrantedAuthority;


/**
 * @author Vipin Kumar
 * @created 31-Jan-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class SentinelAuthority implements GrantedAuthority
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( SentinelAuthority.class );

    private String authority;


    /**
     * 
     */
    public SentinelAuthority( String authority )
    {
        this.authority = authority;
    }


    /* (non-Javadoc)
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    @Override
    public String getAuthority()
    {
        LOG.trace( "Method: getAuthority called." );

        return authority;

    }


    @Override
    public int hashCode()
    {
        return authority.hashCode();
    }


    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
            return false;
        if ( !( obj instanceof SentinelAuthority ) )
            return false;
        return ( (SentinelAuthority) obj ).getAuthority().equals( authority );
    }
}
