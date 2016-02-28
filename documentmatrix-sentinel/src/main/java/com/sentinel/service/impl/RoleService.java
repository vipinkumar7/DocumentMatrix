/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sentinel.persistence.models.Permission;
import com.sentinel.persistence.models.Role;
import com.sentinel.persistence.repository.PermissionRepository;
import com.sentinel.persistence.repository.RoleRepository;
import com.sentinel.service.IRoleService;


/**
 * @author Vipin Kumar
 * @created 23-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Service
@Transactional
public class RoleService implements IRoleService
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( RoleService.class );

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PermissionRepository permissionRepository;


    /* (non-Javadoc)
     * @see com.sentinel.service.IRoleService#createRole(java.lang.String, java.util.Set)
     */
    @Override
    public Role createRole( String name, Set<String> permissions )
    {
        LOG.trace( "Method: createRole called." );

        Role role = roleRepository.findByName( name );
        if ( role == null ) {
            role = new Role( name );
        }
        List<Permission> newPermissions = new ArrayList<>();
        if ( permissions != null ) {
            for ( String newPermission : permissions ) {
                Permission permission = permissionRepository.findByName( newPermission );
                if ( permission == null ) {
                    newPermissions.add( new Permission( newPermission ) );
                }
            }
            role.setPermissions( newPermissions );
        }
        return roleRepository.save( role );
    }
}
