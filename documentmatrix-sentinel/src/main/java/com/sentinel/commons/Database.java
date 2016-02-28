/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.commons;

/**
 * @author Vipin Kumar
 * @created 24-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public enum Database
{

    ORIENT( "ORIENT" ), HIVE( "HIVE" ), HBASE( "HBASE" ), ELASTIC( "ELASTIC" );

    private String name;


    Database( String name )
    {
        this.name = name;
    }


    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName( String name )
    {
        this.name = name;
    }


}
