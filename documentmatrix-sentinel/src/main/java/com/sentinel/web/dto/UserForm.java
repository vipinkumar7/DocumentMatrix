/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.web.dto;


/**
 * @author Vipin Kumar
 * @created 24-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class UserForm
{
    private String firstName;

    private String lastName;

    private String password;

    private String email;


    /**
     * @return the firstName
     */
    public String getFirstName()
    {
        return firstName;
    }


    /**
     * @param firstName the firstName to set
     */
    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }


    /**
     * @return the lastName
     */
    public String getLastName()
    {
        return lastName;
    }


    /**
     * @param lastName the lastName to set
     */
    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }


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
