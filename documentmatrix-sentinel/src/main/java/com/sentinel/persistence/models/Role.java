/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.persistence.models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author Vipin Kumar
 * @created 28-Jan-2016
 * 
 *          TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Entity
public class Role
{

    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToMany ( mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<User> users;

    @ManyToMany ( fetch = FetchType.EAGER)
    @JoinTable ( name = "roles_permissions", joinColumns = @JoinColumn ( name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn ( name = "permission_id", referencedColumnName = "id"))
    private Collection<Permission> permissions;

    private String name;


    public Role()
    {
        super();
    }


    public Role( final String name )
    {
        super();
        this.name = name;
    }


    //

    public Long getId()
    {
        return id;
    }


    public void setId( final Long id )
    {
        this.id = id;
    }


    public String getName()
    {
        return name;
    }


    public void setName( final String name )
    {
        this.name = name;
    }


    public Collection<User> getUsers()
    {
        return users;
    }


    public void setUsers( final Collection<User> users )
    {
        this.users = users;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        return result;
    }


    @Override
    public boolean equals( final Object obj )
    {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Role role = (Role) obj;
        if ( !role.equals( role.name ) ) {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "Role [name=" ).append( name ).append( "]" ).append( "[id=" ).append( id ).append( "]" );
        return builder.toString();
    }


    /**
     * @return the permissions
     */
    public Collection<Permission> getPermissions()
    {
        return permissions;
    }


    /**
     * @param permissions the permissions to set
     */
    public void setPermissions( final Collection<Permission> permissions )
    {
        this.permissions = permissions;
    }
}