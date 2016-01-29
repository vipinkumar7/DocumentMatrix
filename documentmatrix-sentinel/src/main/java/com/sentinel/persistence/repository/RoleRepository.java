/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sentinel.persistence.models.Role;


/**
 * @author Vipin Kumar
 * @created 28-Jan-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findByName( String name );
}
