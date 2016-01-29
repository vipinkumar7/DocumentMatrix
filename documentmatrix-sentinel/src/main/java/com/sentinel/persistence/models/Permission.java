/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.persistence.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


/**
 * @author Vipin Kumar
 * @created 28-Jan-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Entity
public class Permission
{

    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany ( mappedBy = "permissions")
    private Collection<Role> roles;


    public Permission()
    {
        super();
    }


    public Permission( final String name )
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


    public Collection<Role> getRoles()
    {
        return roles;
    }


    public void setRoles( final Collection<Role> roles )
    {
        this.roles = roles;
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
        final Permission permission = (Permission) obj;
        if ( !permission.equals( permission.name ) ) {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "Permission [name=" ).append( name ).append( "]" ).append( "[id=" ).append( id ).append( "]" );
        return builder.toString();
    }
}
