/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.util;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author Vipin Kumar
 * @created 12-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class SecurityUtil
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( SecurityUtil.class );


    public static String getUserName()
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ( authentication.getPrincipal() instanceof UserDetails ) {
            return ( (UserDetails) authentication.getPrincipal() ).getUsername();
        } else {
            return authentication.getPrincipal().toString();
        }
    }


    public static ArrayList<String> getUserRoles()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        ArrayList<String> currentUserRoles = new ArrayList<String>();
        for ( GrantedAuthority authority : authorities ) {
            currentUserRoles.add( authority.getAuthority() );
        }
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( "currentUserRoles:" + currentUserRoles );
        }
        return currentUserRoles;
    }


    public static boolean doesUserHasRole( String role )
    {
        ArrayList<String> currentUserRoles = getUserRoles();
        for ( String s : currentUserRoles ) {
            if ( s.equals( role ) ) {
                return true;
            }
        }
        return false;
    }

}
