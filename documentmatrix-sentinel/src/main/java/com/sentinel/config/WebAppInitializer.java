/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * @author Vipin Kumar
 * @created 29-Jan-2016
 * 
 *          TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( WebAppInitializer.class );


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.support.
     * AbstractAnnotationConfigDispatcherServletInitializer
     * #getRootConfigClasses()
     */
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        LOG.trace( "Method: getRootConfigClasses called." );

        return new Class<?>[] { AppConfig.class, PersistenceJPAConfig.class };
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.support.
     * AbstractAnnotationConfigDispatcherServletInitializer
     * #getServletConfigClasses()
     */
    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        LOG.trace( "Method: getServletConfigClasses called." );

        return new Class<?>[] { SecSecurityConfig.class, Oauth2ServerConfig.class,MvcConfig.class };

    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.support.AbstractDispatcherServletInitializer
     * #onStartup(javax.servlet.ServletContext)
     */
    @Override
    public void onStartup( ServletContext servletContext ) throws ServletException
    {
        LOG.trace( "Method: onStartup called." );

        super.onStartup( servletContext );
        DelegatingFilterProxy filter = new DelegatingFilterProxy( "springSecurityFilterChain" );
        filter.setContextAttribute( "org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher" );
        servletContext.addFilter( "springSecurityFilterChain", filter ).addMappingForUrlPatterns( null, false, "/*" );

        LOG.trace( "Method: onStartup finished." );
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.support.AbstractDispatcherServletInitializer
     * #getServletMappings()
     */
    @Override
    protected String[] getServletMappings()
    {
        LOG.trace( "Method: getServletMappings called." );

        return new String[] { "/" };
    }
}
