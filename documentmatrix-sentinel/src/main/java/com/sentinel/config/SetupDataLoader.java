package com.sentinel.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sentinel.commons.CommonConstants;
import com.sentinel.persistence.models.Permission;
import com.sentinel.persistence.models.Role;
import com.sentinel.persistence.models.User;
import com.sentinel.persistence.repository.PermissionRepository;
import com.sentinel.persistence.repository.RoleRepository;
import com.sentinel.persistence.repository.UserRepository;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>
{

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // API

    @Override
    @Transactional
    public void onApplicationEvent( final ContextRefreshedEvent event )
    {
        if ( alreadySetup ) {
            return;
        }
        System.out.println( "==============================" );
        /**
         *== create initial permissions
         *Admin for all database should be initialized at start time 
         */
        final Permission superPermission = createPermissionIfNotFound( CommonConstants.SUPER_ADMIN_PRIVILEGE );
        final Permission orientPermission = createPermissionIfNotFound( CommonConstants.ORIENT_ADMIN_PRIVILEGE );
        final Permission simpleUserPermission = createPermissionIfNotFound( CommonConstants.SIMPLE_USER_PRIVILEGE );
        final Permission nonePermission = createPermissionIfNotFound( CommonConstants.NONE_PRIVILEGE );

        // == create initial roles
        final List<Permission> powerAdminPermission = Arrays.asList( superPermission, orientPermission );
        createRoleIfNotFound( CommonConstants.ROLE_POWER_ADMIN, powerAdminPermission );
        createRoleIfNotFound( CommonConstants.ROLE_ORIENT_ADMIN, Arrays.asList( orientPermission ) );
        createRoleIfNotFound( CommonConstants.ROLE_SIMPLE_USER, Arrays.asList( simpleUserPermission ) );
        createRoleIfNotFound( CommonConstants.ROLE_NONE, Arrays.asList( nonePermission ) );

        final Role adminRole = roleRepository.findByName( CommonConstants.ROLE_POWER_ADMIN );
        final User user = new User();
        user.setFirstName( "power" );
        user.setLastName( "admin" );
        user.setPassword( passwordEncoder.encode( "power_admin" ) );
        user.setEmail( "power.admin@test.com" );
        user.setRoles( Arrays.asList( adminRole ) );
        user.setEnabled( true );
        userRepository.save( user );

        final Role adminRole1 = roleRepository.findByName( CommonConstants.ROLE_ORIENT_ADMIN );
        final User user1 = new User();
        user1.setFirstName( "admin" );
        user1.setLastName( "admin" );
        user1.setPassword( passwordEncoder.encode( "admin" ) );
        user1.setEmail( "orient.admin@test.com" );
        user1.setRoles( Arrays.asList( adminRole1 ) );
        user1.setEnabled( true );
        userRepository.save( user1 );
        alreadySetup = true;
    }


    private final Permission createPermissionIfNotFound( final String name )
    {
        Permission privilege = permissionRepository.findByName( name );
        if ( privilege == null ) {
            privilege = new Permission( name );
            permissionRepository.save( privilege );
        }
        return privilege;
    }


    private final Role createRoleIfNotFound( final String name, final Collection<Permission> privileges )
    {
        Role role = roleRepository.findByName( name );
        if ( role == null ) {
            role = new Role( name );
            role.setPermissions( privileges );
            roleRepository.save( role );
        }
        return role;
    }

}