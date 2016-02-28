/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sentinel.persistence.models.Role;
import com.sentinel.persistence.models.User;
import com.sentinel.persistence.repository.UserRepository;
import com.sentinel.service.IUserService;
import com.sentinel.service.OrientDbService;


/**
 * @author Vipin Kumar
 * @created 28-Jan-2016
 * 
 *          TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Controller
@RequestMapping ( value = "/orient/api")
public class OrientDbController
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( OrientDbController.class );

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OrientDbService orientdbService;


    /**
     * 
     * @param TABLE_NAME
     * @param orientAdminRequest
     */
    @RequestMapping ( value = "/create/all/{TABLE_NAME}/{DATABASE}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize ( "hasRole('ORIENT_ADMIN_PRIVILEGE')")
    public ResponseEntity<String> createTableandAdmin( @PathVariable String TABLE_NAME, @PathVariable String DATABASE )
    {
        LOG.debug( "Creating all Roles and Permission for Table" + TABLE_NAME );
        orientdbService.createAssociatedRolesAndPermission( TABLE_NAME, DATABASE );
        return new ResponseEntity<String>( "associated roles and permission creaded ", HttpStatus.OK );
    }


    /**
     * 
     * @param user
     * @param role
     * @return
     */
    @RequestMapping ( value = "/users/{user}/grant/role/{role}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize ( value = "hasRole('ORIENT_ADMIN_PRIVILEGE')")
    public ResponseEntity<String> grantRole( @PathVariable User user, @PathVariable Role role )
    {
        if ( user == null ) {
            return new ResponseEntity<String>( "invalid user id", HttpStatus.UNPROCESSABLE_ENTITY );
        }
        userService.grantRole( user, role );


        boolean found = orientdbService.checkForUserInOrient( user.getEmail() )
            && orientdbService.checkForRoleInOrient( role.getName() );
        if ( found ) {
            orientdbService.createRole( user.getEmail(), role.getName() );
            userRepository.saveAndFlush( user );
        } else
            return new ResponseEntity<String>( "user or role already exist", HttpStatus.UNPROCESSABLE_ENTITY );
        return new ResponseEntity<String>( "role granted", HttpStatus.OK );


    }


    /**
     * 
     * @param user
     * @param role
     * @return
     */
    @RequestMapping ( value = "/users/{user}/revoke/role/{role}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize ( value = "hasRole('ORIENT_ADMIN_PRIVILEGE')")
    public ResponseEntity<String> revokeRole( @PathVariable User user, @PathVariable Role role )
    {
        if ( user == null ) {
            return new ResponseEntity<String>( "invalid user id", HttpStatus.UNPROCESSABLE_ENTITY );
        }
        userService.revokeRole( user, role );
        userRepository.saveAndFlush( user );
        return new ResponseEntity<String>( "role revoked", HttpStatus.OK );
    }


    /**
     * get all the users 
     * @return
     */
    @RequestMapping ( value = "/users", method = RequestMethod.GET)
    @PreAuthorize ( value = "hasRole('ORIENT_ADMIN_PRIVILEGE')")
    @ResponseBody
    public List<User> list()
    {
        return userRepository.findAll();
    }

}
