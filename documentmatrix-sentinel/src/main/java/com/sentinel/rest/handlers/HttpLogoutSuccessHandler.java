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

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;


/**
 * @author Vipin Kumar
 * @created 19-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Component
public class HttpLogoutSuccessHandler implements LogoutSuccessHandler
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( HttpLogoutSuccessHandler.class );


    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onLogoutSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication )
        throws IOException, ServletException
    {
        LOG.trace( "Method: onLogoutSuccess called." );

        response.setStatus( HttpServletResponse.SC_OK );
        response.getWriter().flush();
        
        LOG.trace( "Method: onLogoutSuccess finished." );
    }
}
