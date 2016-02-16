/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sentinel.persistence.models.User;
import com.sentinel.persistence.repository.UserRepository;
import com.sentinel.service.OrientDbService;
import com.sentinel.web.dto.OrientClassAdminRequest;


/**
 * @author Vipin Kumar
 * @created 28-Jan-2016
 * 
 *          TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Controller
@RequestMapping ( value = "/graph")
public class OrientDbUserController
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( OrientDbUserController.class );

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OrientDbService orientdbService;


    @RequestMapping ( value = "/show/{TABLE_NAME}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize ( "hasRole('READ_PRIVILEGE')")
    public String showRecords( @PathVariable String TABLE_NAME )// TODO
                                                                // //@AuthenticationPrincipal
                                                                // User user1
    {
        final String user = SecurityContextHolder.getContext().getAuthentication().getName();
        User usr = userRepository.findByEmail( user );
        return orientdbService.getData( TABLE_NAME, usr.getFirstName() );
    }


    @RequestMapping ( value = "/grant/{TABLE_NAME}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize ( "hasRole('WRITE_PRIVILEGE')")
    public void giveAccess( @PathVariable String TABLE_NAME )
    {
        final String user = SecurityContextHolder.getContext().getAuthentication().getName();
        User usr = userRepository.findByEmail( user );
        orientdbService.grantAccess( usr.getFirstName() );
    }
    
    

    @RequestMapping(value="/create/class")
    @PreAuthorize("hasRole('ORIENT_ADMIN')")
    public void createTableandAdmin(@RequestBody  OrientClassAdminRequest classAdminRequest){
        
    }
    
    @RequestMapping(value="/change/password")
    @PreAuthorize("hasRole('POWER_ADMIN')")
    public void changeAdminPassword(){
        
    }
    
}
