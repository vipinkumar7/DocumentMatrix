/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

/**
 * @author Vipin Kumar
 * @created 16-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class PublicJWTConfig
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( PublicJWTConfig.class );
   
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;


    @Bean
    @Qualifier ( "tokenStore")
    public TokenStore tokenStore()
    {

        System.out.println( "Created JwtTokenStore" );
        return new JwtTokenStore( jwtAccessTokenConverter );
    }


    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer()
    {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource( "pub.cert" );
        String publicKey = null;
        try {
            publicKey = new String( FileCopyUtils.copyToByteArray( resource.getInputStream() ) );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        converter.setVerifierKey( publicKey );
        return converter;
    }
}
