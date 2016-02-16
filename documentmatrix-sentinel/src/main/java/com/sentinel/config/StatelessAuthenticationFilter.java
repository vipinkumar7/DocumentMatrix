/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.google.common.base.Preconditions;


/**
 * @author Vipin Kumar
 * @created 16-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class StatelessAuthenticationFilter extends GenericFilterBean
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( StatelessAuthenticationFilter.class );

    private final TokenAuthenticationService tokenAuthenticationService;


    /**
     * 
     */
    public StatelessAuthenticationFilter( TokenAuthenticationService tokenAuthenticationService )
    {
        this.tokenAuthenticationService = Preconditions.checkNotNull( tokenAuthenticationService );
    }


    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException,
        ServletException
    {
        LOG.trace( "Method: doFilter called." );

        Authentication authentication = tokenAuthenticationService.getAuthentication( (HttpServletRequest) request );
        SecurityContextHolder.getContext().setAuthentication( authentication );
        chain.doFilter( request, response );
        // SecurityContextHolder.getContext().setAuthentication( null );

        LOG.trace( "Method: doFilter finished." );
    }
}
