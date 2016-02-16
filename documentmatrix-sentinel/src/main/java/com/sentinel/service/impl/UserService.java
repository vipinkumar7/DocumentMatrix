/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.service.impl;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sentinel.exceptions.EmailExistsException;
import com.sentinel.persistence.models.User;
import com.sentinel.persistence.repository.RoleRepository;
import com.sentinel.persistence.repository.UserRepository;
import com.sentinel.service.IUserService;
import com.sentinel.web.dto.UserDto;

/**
 * @author Vipin Kumar
 * @created 09-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */

@Service
@Transactional
public class UserService implements IUserService
{
    
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @Autowired
    private RoleRepository roleRepository;
    
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( UserService.class );

    /* (non-Javadoc)
     * @see com.sentinel.service.IUserService#registerNewUserAccount(com.sentinel.web.dto.UserDto)
     */
    @Override
    public User registerNewUserAccount( UserDto accountDto ) throws EmailExistsException
    {
        LOG.trace("Method: registerNewUserAccount called.");
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email adress: " + accountDto.getEmail());
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_NONE")));
        return repository.save(user);    
        
    }

    /* (non-Javadoc)
     * @see com.sentinel.service.IUserService#getUser(java.lang.String)
     */
    @Override
    public User getUser( String verificationToken )
    {
        LOG.trace("Method: getUser called.");
        
        return null;
        
    }

    /* (non-Javadoc)
     * @see com.sentinel.service.IUserService#saveRegisteredUser(com.sentinel.persistence.models.User)
     */
    @Override
    public void saveRegisteredUser( User user )
    {
        LOG.trace("Method: saveRegisteredUser called.");
        repository.save( user );
        LOG.trace("Method: saveRegisteredUser finished.");
    }

    /* (non-Javadoc)
     * @see com.sentinel.service.IUserService#deleteUser(com.sentinel.persistence.models.User)
     */
    @Override
    public void deleteUser( User user )
    {
        LOG.trace("Method: deleteUser called.");
        
        
        LOG.trace("Method: deleteUser finished.");
    }

    /* (non-Javadoc)
     * @see com.sentinel.service.IUserService#createVerificationTokenForUser(com.sentinel.persistence.models.User, java.lang.String)
     */
    @Override
    public void createVerificationTokenForUser( User user, String token )
    {
        LOG.trace("Method: createVerificationTokenForUser called.");
        
        
        LOG.trace("Method: createVerificationTokenForUser finished.");
    }

    /* (non-Javadoc)
     * @see com.sentinel.service.IUserService#createPasswordResetTokenForUser(com.sentinel.persistence.models.User, java.lang.String)
     */
    @Override
    public void createPasswordResetTokenForUser( User user, String token )
    {
        LOG.trace("Method: createPasswordResetTokenForUser called.");
        
        
        LOG.trace("Method: createPasswordResetTokenForUser finished.");
    }

    /* (non-Javadoc)
     * @see com.sentinel.service.IUserService#findUserByEmail(java.lang.String)
     */
    @Override
    public User findUserByEmail( String email )
    {
        LOG.trace("Method: findUserByEmail called.");
        
        return null;
        
    }

    /* (non-Javadoc)
     * @see com.sentinel.service.IUserService#getUserByID(long)
     */
    @Override
    public User getUserByID( long id )
    {
        LOG.trace("Method: getUserByID called.");
        
        return null;
        
    }

    /* (non-Javadoc)
     * @see com.sentinel.service.IUserService#changeUserPassword(com.sentinel.persistence.models.User, java.lang.String)
     */
    @Override
    public void changeUserPassword( User user, String password )
    {
        LOG.trace("Method: changeUserPassword called.");
        
        
        LOG.trace("Method: changeUserPassword finished.");
    }

    /* (non-Javadoc)
     * @see com.sentinel.service.IUserService#checkIfValidOldPassword(com.sentinel.persistence.models.User, java.lang.String)
     */
    @Override
    public boolean checkIfValidOldPassword( User user, String password )
    {
        LOG.trace("Method: checkIfValidOldPassword called.");
        
        return false;
        
    }
    
    private boolean emailExist(final String email) {
        final User user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
