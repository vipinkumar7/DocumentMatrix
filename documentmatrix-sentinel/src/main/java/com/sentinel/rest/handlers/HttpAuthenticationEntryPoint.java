/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.rest.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


/**
 * @author Vipin Kumar
 * @created 19-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Component
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( HttpAuthenticationEntryPoint.class );


    /* (non-Javadoc)
     * @see org.springframework.security.web.AuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void commence( HttpServletRequest request, HttpServletResponse response, AuthenticationException authException )
        throws IOException, ServletException
    {
        LOG.trace( "Method: commence called." );

        response.sendError( HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage() );
        LOG.trace( "Method: commence finished." );
    }
}
