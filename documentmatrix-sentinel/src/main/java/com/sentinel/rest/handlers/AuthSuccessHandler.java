/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.rest.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentinel.config.UserAuthentication;


/**
 * @author Vipin Kumar
 * @created 19-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( AuthSuccessHandler.class );

    private final ObjectMapper mapper;


    /**
     * 
     */
    @Autowired
    public AuthSuccessHandler( MappingJackson2HttpMessageConverter converter )
    {
        this.mapper = converter.getObjectMapper();
    }


    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication )
        throws ServletException, IOException
    {
        LOG.trace( "Method: onAuthenticationSuccess called." );

        response.setStatus( HttpServletResponse.SC_OK );
        UserDetails userdetails = (UserDetails) authentication.getPrincipal();

        
        LOG.info( userdetails.getUsername() + " got is connected " );

        PrintWriter writer = response.getWriter();
        mapper.writeValue( writer, null );
        writer.flush();
        LOG.trace( "Method: onAuthenticationSuccess finished." );
    }
}
