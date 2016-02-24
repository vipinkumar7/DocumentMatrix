/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.web.controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentinel.config.UserAuthentication;
import com.sentinel.persistence.models.User;
import com.sentinel.persistence.models.UserRequest;
import com.sentinel.persistence.repository.UserRepository;
import com.sentinel.persistence.repository.UserRequestRepository;
import com.sentinel.web.dto.OrientRequestDetails;


/**
 * @author Vipin Kumar
 * @created 24-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */

@RequestMapping ( value = "/request")
public class RequestController
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( RequestController.class );


    @Autowired
    private UserRequestRepository userRequestRepository;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping ( value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> createRequest( @RequestBody UserRequest userRequest )
    {
        LOG.debug( "creating new Request " );
        try {
            new ObjectMapper().readValue( userRequest.getRequestDetails().trim(), OrientRequestDetails.class );
        } catch ( Exception e ) {
            LOG.error( "Exception while performing operation in createRequest", e );
            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
        }
        UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail( userEmail );
        Collection<User> collection = new ArrayList<User>();
        collection.add( user );
        userRequest.setUsers( collection );
        userRequestRepository.saveAndFlush( userRequest );
        return new ResponseEntity<Void>( HttpStatus.CREATED );
    }


}
