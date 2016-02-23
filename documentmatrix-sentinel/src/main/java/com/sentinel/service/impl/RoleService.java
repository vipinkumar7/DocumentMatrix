/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

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
    public Role createRole( String name, Set<Permission> permissions )
    {
        LOG.trace( "Method: createRole called." );

        return null;

    }
}
