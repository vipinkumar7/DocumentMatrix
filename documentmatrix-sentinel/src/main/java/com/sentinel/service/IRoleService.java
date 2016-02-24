/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.service;

import java.util.Set;

import com.sentinel.persistence.models.Role;


/**
 * @author Vipin Kumar
 * @created 23-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public interface IRoleService
{
    Role createRole( String name, Set<String> permissions );
}
