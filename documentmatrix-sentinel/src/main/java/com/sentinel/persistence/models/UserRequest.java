/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.persistence.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column ( columnDefinition = "TEXT")
    private Database databaseType;
    @Column ( columnDefinition = "TEXT")
    private RequestType requestType;

    @JsonIgnore
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
    @Enumerated ( EnumType.STRING)
    public void setRequestDetails( String requestDetails )
    {
        this.requestDetails = requestDetails;
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


    /**
     * @return the databaseType
     */
    @Enumerated ( EnumType.STRING)
    public Database getDatabaseType()
    {
        return databaseType;
    }


    /**
     * @param databaseType the databaseType to set
     */
    public void setDatabaseType( Database databaseType )
    {
        this.databaseType = databaseType;
    }

}
