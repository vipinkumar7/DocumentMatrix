/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.web.dto;

/**
 * @author Vipin Kumar
 * @created 12-Feb-2016
 * 
 * request for new creating new class in orient database
 * 
 */
public class OrientClassAdminRequest
{

    private String email;
    private String orientClassName;
    private String orientDatabse;
    
    
    /**
     * @return the orientClassName
     */
    public String getOrientClassName()
    {
        return orientClassName;
    }
    /**
     * @param orientClassName the orientClassName to set
     */
    public void setOrientClassName( String orientClassName )
    {
        this.orientClassName = orientClassName;
    }
    /**
     * @return the orientDatabse
     */
    public String getOrientDatabse()
    {
        return orientDatabse;
    }
    /**
     * @param orientDatabse the orientDatabse to set
     */
    public void setOrientDatabse( String orientDatabse )
    {
        this.orientDatabse = orientDatabse;
    }

    
}
