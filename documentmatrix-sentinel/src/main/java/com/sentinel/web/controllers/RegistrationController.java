/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;
import com.sentinel.exceptions.EmailExistsException;
import com.sentinel.persistence.models.User;
import com.sentinel.service.IUserService;
import com.sentinel.web.dto.UserDto;


/**
 * @author Vipin Kumar
 * @created 09-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Controller
public class RegistrationController
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( RegistrationController.class );

    @Autowired
    private IUserService userService;


    @RequestMapping ( value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser( @RequestBody UserDto accountDto, UriComponentsBuilder ucBuilder )
    {

        LOG.debug( "Registring new user" );
        final User registered = createUserAccount( accountDto );
        if ( registered == null ) {
            return new ResponseEntity<Void>( HttpStatus.CONFLICT );
        }
        /* 
        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getUsername() + " already exist");
        }
        */
        userService.saveRegisteredUser( registered );
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( ucBuilder.path( "/user/{id}" ).buildAndExpand( registered.getId() ).toUri() );//TODO
        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }


    private User createUserAccount( final UserDto accountDto )
    {
        User registered = null;
        try {
            registered = userService.registerNewUserAccount( accountDto );
        } catch ( final EmailExistsException e ) {
            return null;
        }
        return registered;
    }

}
