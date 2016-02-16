/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author Vipin Kumar
 * @created 12-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class PasswordEncoderDecoder implements PasswordEncoder
{
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( PasswordEncoderDecoder.class );
    private StandardPBEStringEncryptor encoderdecoder;


    /**
     * 
     */
    public PasswordEncoderDecoder()
    {
        encoderdecoder = new StandardPBEStringEncryptor();
    }


    /* (non-Javadoc)
     * @see org.springframework.security.crypto.password.PasswordEncoder#encode(java.lang.CharSequence)
     */
    @Override
    public String encode( CharSequence rawPassword )
    {
        LOG.trace( "Method: encode called." );

        return encoderdecoder.encrypt( rawPassword.toString().trim() );
    }


    /* (non-Javadoc)
     * @see org.springframework.security.crypto.password.PasswordEncoder#matches(java.lang.CharSequence, java.lang.String)
     */
    @Override
    public boolean matches( CharSequence rawPassword, String encodedPassword )
    {
        LOG.trace( "Method: matches called." );
        return encoderdecoder.decrypt( encodedPassword ).equals( rawPassword );

    }


    public String decode( String password )
    {
        return encoderdecoder.decrypt( password );
    }
}
