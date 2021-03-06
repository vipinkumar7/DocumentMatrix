/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author Vipin Kumar
 * @created 09-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class UserDto
{


    @JsonProperty ( "password")
    private String password;


    @JsonProperty ( "username")
    private String email;


    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }


    /**
     * @param password the password to set
     */
    public void setPassword( String password )
    {
        this.password = password;
    }


    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }


    /**
     * @param email the email to set
     */
    public void setEmail( String email )
    {
        this.email = email;
    }


}
