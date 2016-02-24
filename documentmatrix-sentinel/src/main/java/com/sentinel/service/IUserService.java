/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.service;

import com.sentinel.exceptions.EmailExistsException;
import com.sentinel.persistence.models.User;
import com.sentinel.web.dto.UserForm;


/**
 * @author Vipin Kumar
 * @created 09-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public interface IUserService
{

    User registerNewUserAccount( UserForm accountDto ) throws EmailExistsException;


    User getUser( String verificationToken );


    void saveRegisteredUser( User user );


    void deleteUser( User user );


    void createVerificationTokenForUser( User user, String token );


    void createPasswordResetTokenForUser( User user, String token );


    User findUserByEmail( String email );


    User getUserByID( long id );


    void changeUserPassword( User user, String password );


    boolean checkIfValidOldPassword( User user, String password );

}
