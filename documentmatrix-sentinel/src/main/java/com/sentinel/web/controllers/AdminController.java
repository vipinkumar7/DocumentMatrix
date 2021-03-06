/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sentinel.persistence.models.Role;
import com.sentinel.persistence.models.User;
import com.sentinel.persistence.repository.RoleRepository;
import com.sentinel.persistence.repository.UserRepository;
import com.sentinel.service.IUserService;


/**
 * @author Vipin Kumar
 * @created 25-Feb-2016
 * 
 * Services for Power admin 
 * 
 */
@Controller
@RequestMapping ( "/admin")
public class AdminController
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( AdminController.class );

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @RequestMapping ( value = "/users/{userId}/grant/role", method = RequestMethod.POST)
    @PreAuthorize ( value = "hasRole('SUPER_ADMIN_PRIVILEGE')")
    @ResponseBody
    public ResponseEntity<String> grantSimpleRole( @PathVariable Long userId )
    {
        User user = userRepository.findOne( userId );
        LOG.debug( "Allow user for Simle user permissions" );
        if ( user == null ) {
            return new ResponseEntity<String>( "invalid user id", HttpStatus.UNPROCESSABLE_ENTITY );
        }
        Role role = roleRepository.findByName( "ROLE_SIMPLE_USER" );
        userService.grantRole( user, role );
        user.setEnabled( true );
        userRepository.saveAndFlush( user );
        return new ResponseEntity<String>( "role granted", HttpStatus.OK );
    }
}
