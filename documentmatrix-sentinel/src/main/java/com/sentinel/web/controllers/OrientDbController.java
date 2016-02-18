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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sentinel.persistence.models.Role;
import com.sentinel.persistence.models.User;
import com.sentinel.persistence.repository.UserRepository;
import com.sentinel.service.OrientDbService;
import com.sentinel.service.impl.UserService;
import com.sentinel.web.dto.OrientAdminRequest;


/**
 * @author Vipin Kumar
 * @created 28-Jan-2016
 * 
 *          TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Controller
@RequestMapping ( value = "/admin/orient/api")
public class OrientDbController
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( OrientDbController.class );

    @Autowired
    private UserService userService;
    
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
    @RequestMapping ( value = "/create/all/{TABLE_NAME}",method=RequestMethod.POST)
    @PreAuthorize ( "hasRole('ROLE_ADMIN_ORIENT')")
    public void createTableandAdmin(@PathVariable String TABLE_NAME, @RequestBody OrientAdminRequest orientAdminRequest )
    {
        LOG.debug( "Creating all Roles and Permission for Table"+ TABLE_NAME );
        orientdbService.createTableAndItsAdmin(TABLE_NAME,orientAdminRequest);
        orientdbService.createAssociatedRolesAndPermission(TABLE_NAME);
    }
    
    
    /**
     * 
     * @param user
     * @param role
     * @return
     */
    @RequestMapping(value = "/admin/api/users/{user}/grant/role/{role}", method = RequestMethod.POST)
    @PreAuthorize(value="hasRole('ROLE_ADMIN_ORIENT')")
    public ResponseEntity<String> grantRole(@PathVariable User user, @PathVariable Role role) {
        if (user == null) {
            return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userService.grantRole(user,role);
        userRepository.saveAndFlush(user);
        return new ResponseEntity<String>("role granted", HttpStatus.OK);
        
       
    }

    
    /**
     * 
     * @param user
     * @param role
     * @return
     */
    @RequestMapping(value = "/admin/api/users/{user}/revoke/role/{role}", method = RequestMethod.POST)
    @PreAuthorize(value="hasRole('ROLE_ADMIN_ORIENT')")
    public ResponseEntity<String> revokeRole(@PathVariable User user, @PathVariable Role role) {
        if (user == null) {
            return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userService.revokeRole(user,role);
        userRepository.saveAndFlush(user);
        return new ResponseEntity<String>("role revoked", HttpStatus.OK);
    }


 
    /**
     * get all the users 
     * @return
     */
    @PreAuthorize(value="hasRole('ROLE_ADMIN_ORIENT')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> list() {
        return userRepository.findAll();
    }

}
