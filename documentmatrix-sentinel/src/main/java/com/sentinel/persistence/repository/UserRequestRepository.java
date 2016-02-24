/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sentinel.persistence.models.UserRequest;


/**
 * @author Vipin Kumar
 * @created 25-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Transactional ( readOnly = true)
public interface UserRequestRepository extends JpaRepository<UserRequest, Long>
{


}
