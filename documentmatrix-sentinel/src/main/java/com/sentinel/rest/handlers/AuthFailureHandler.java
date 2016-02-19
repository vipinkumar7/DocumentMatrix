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

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


/**
 * @author Vipin Kumar
 * @created 19-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( AuthFailureHandler.class );


    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception ) throws IOException, ServletException
    {
        LOG.trace( "Method: onAuthenticationFailure called." );

        response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
        PrintWriter writer = response.getWriter();
        writer.write( exception.getMessage() );
        writer.flush();
        LOG.trace( "Method: onAuthenticationFailure finished." );
    }

}
