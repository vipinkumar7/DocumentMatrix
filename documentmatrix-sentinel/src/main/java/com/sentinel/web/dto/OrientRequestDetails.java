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
 * @created 24-Feb-2016
 * 
 * 
 */
public class OrientRequestDetails
{
    @JsonProperty ( required = true)
    private String className;
    private String propertyName;
    @JsonProperty ( required = true)
    private String databaseName;
    private String query;


    /**
     * @return the className
     */
    public String getClassName()
    {
        return className;
    }


    /**
     * @param className the className to set
     */
    public void setClassName( String className )
    {
        this.className = className;
    }


    /**
     * @return the propertyName
     */
    public String getPropertyName()
    {
        return propertyName;
    }


    /**
     * @param propertyName the propertyName to set
     */
    public void setPropertyName( String propertyName )
    {
        this.propertyName = propertyName;
    }


    /**
     * @return the databaseName
     */
    public String getDatabaseName()
    {
        return databaseName;
    }


    /**
     * @param databaseName the databaseName to set
     */
    public void setDatabaseName( String databaseName )
    {
        this.databaseName = databaseName;
    }


    /**
     * @return the query
     */
    public String getQuery()
    {
        return query;
    }


    /**
     * @param query the query to set
     */
    public void setQuery( String query )
    {
        this.query = query;
    }
}
