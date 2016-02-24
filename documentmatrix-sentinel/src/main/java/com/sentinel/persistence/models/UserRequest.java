/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.persistence.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.sentinel.commons.Database;
import com.sentinel.commons.RequestType;
import com.sentinel.persistence.models.User;


/**
 * @author Vipin Kumar
 * @created 12-Feb-2016
 * 
 * request for new creating new class in orient database
 * 
 */
@Entity
public class UserRequest
{

    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private Long id;
    private String requestDetails;
    private Database database;
    private RequestType requestType;

    @ManyToMany ( mappedBy = "requests", fetch = FetchType.EAGER)
    private Collection<User> users;


    /**
     * @return the requestDetails
     */
    public String getRequestDetails()
    {
        return requestDetails;
    }


    /**
     * @param requestDetails the requestDetails to set
     */
    public void setRequestDetails( String requestDetails )
    {
        this.requestDetails = requestDetails;
    }


    /**
     * @return the database
     */
    public Database getDatabase()
    {
        return database;
    }


    /**
     * @param database the database to set
     */
    public void setDatabase( Database database )
    {
        this.database = database;
    }


    /**
     * @return the requestType
     */
    public RequestType getRequestType()
    {
        return requestType;
    }


    /**
     * @param requestType the requestType to set
     */
    public void setRequestType( RequestType requestType )
    {
        this.requestType = requestType;
    }


    /**
     * @return the users
     */
    public Collection<User> getUsers()
    {
        return users;
    }


    /**
     * @param users the users to set
     */
    public void setUsers( Collection<User> users )
    {
        this.users = users;
    }


    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }


    /**
     * @param id the id to set
     */
    public void setId( Long id )
    {
        this.id = id;
    }

}
