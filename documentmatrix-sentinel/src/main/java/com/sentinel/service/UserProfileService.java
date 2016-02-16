/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sentinel.persistence.models.Permission;
import com.sentinel.persistence.models.Role;
import com.sentinel.persistence.models.User;
import com.sentinel.persistence.repository.UserRepository;


/**
 * @author Vipin Kumar
 * @created 28-Jan-2016
 * 
 *          TODO: Write a quick description of what the class is supposed to do.
 * 
 */
/*
@Service ( "userDetailsService")
@Transactional*/
public class UserProfileService implements UserDetailsService
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( UserProfileService.class );

    private final UserRepository userRepository;


    /**
     * 
     */
    public UserProfileService( UserRepository userRepository )
    {
        this.userRepository = userRepository;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername( String email )
        throws UsernameNotFoundException
    {
        LOG.trace( "Method: loadUserByUsername called." );
        try {
            final User user = userRepository.findByEmail( email );
            if ( user == null ) {
                throw new UsernameNotFoundException( "No user found with username: " + email );
            }

            return new org.springframework.security.core.userdetails.User( user.getEmail(), user.getPassword(),
                user.isEnabled(), true, true, true, getAuthorities( user.getRoles() ) );
        } catch ( final Exception e ) {
            throw new RuntimeException( e );
        }
    }


    public final Collection<? extends GrantedAuthority> getAuthorities( final Collection<Role> roles )
    {
        return getGrantedAuthorities( getPermissions( roles ) );
    }


    private final List<String> getPermissions( final Collection<Role> roles )
    {
        final List<String> permissions = new ArrayList<String>();
        final List<Permission> collection = new ArrayList<Permission>();
        for ( final Role role : roles ) {
            collection.addAll( role.getPermissions() );
        }
        for ( final Permission item : collection ) {
            permissions.add( item.getName() );
        }
        return permissions;
    }


    private final List<GrantedAuthority> getGrantedAuthorities( final List<String> permissions )
    {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for ( final String permission : permissions ) {
            authorities.add( new SimpleGrantedAuthority( permission ) );
        }
        return authorities;
    }
}
