/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.config;

import java.security.KeyPair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;


/**
 * @author Vipin Kumar
 * @created 15-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Configuration
public class Oauth2ServerConfig
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( Oauth2ServerConfig.class );

    private static final String SENTINEL_RESOURCE_ID = "sentinel";


    @Configuration
    @EnableResourceServer
    @Import(PublicJWTConfig.class)
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter
    {

        @Autowired
        TokenStore tokenStore;


        @Autowired
        JwtAccessTokenConverter tokenConverter;


        /* (non-Javadoc)
         * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer)
         */
        @Override
        public void configure( ResourceServerSecurityConfigurer resources ) throws Exception
        {
            LOG.trace( "Method: configure called." );

            resources.resourceId( SENTINEL_RESOURCE_ID ).tokenStore( tokenStore );

            LOG.trace( "Method: configure finished." );
        }


        /* (non-Javadoc)
         * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
         */
        @Override
        public void configure( HttpSecurity http ) throws Exception
        {
            LOG.trace( "Method: configure called." );

            // @formatter:off
           /* http.sessionManagement().
            sessionCreationPolicy( SessionCreationPolicy.NEVER );*/
            http.authorizeRequests().anyRequest().authenticated();
         // @formatter:on
            LOG.trace( "Method: configure finished." );
        }

    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter
    {

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        
        @Bean
        public TokenStore tokenStore()
        {
            return new JwtTokenStore( jwtTokenEnhancer() );
        }


        @Bean
        protected JwtAccessTokenConverter jwtTokenEnhancer()
        {
            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory( new ClassPathResource( "server.jks" ),
                "letmein".toCharArray() );
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            KeyPair keyPair=keyStoreKeyFactory.getKeyPair( "mytestkey", "changeme".toCharArray() );
            converter.setKeyPair( keyPair);
            return converter;
        }
        
        
    }
}
