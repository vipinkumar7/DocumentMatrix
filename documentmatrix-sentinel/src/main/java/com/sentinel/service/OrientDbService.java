/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orientechnologies.orient.core.Orient;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.OMetadataDefault;
import com.orientechnologies.orient.core.metadata.security.ORole;
import com.orientechnologies.orient.core.metadata.security.ORule;
import com.orientechnologies.orient.core.metadata.security.OSecurity;
import com.orientechnologies.orient.core.metadata.security.OSecurityRole;
import com.sentinel.graph.orientdb.OrientRole;
import com.sentinel.service.impl.UserService;
import com.sentinel.web.dto.OrientAdminRequest;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;


/**
 * @author Vipin Kumar
 * @created 31-Jan-2016
 * 
 *          TODO: Write a quick description of what the class is supposed to do.
 * 
 */

@Service ( "orientDbService")
@Transactional
public class OrientDbService
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( OrientDbService.class );

    private final String path = "remote:localhost/vipin";

    @Autowired
    private UserService userService;


    @PostConstruct
    public void init()
    {
        LOG.debug( "Orient shutdownhook " );
        Orient.instance().removeShutdownHook();
    }


    @PreDestroy
    public void shutdown()
    {
        Orient.instance().shutdown();
    }


    /**
     * 
     */
    public void createTableAndItsAdmin( String TABLE_NAME, OrientAdminRequest orientAdminRequest )
    {
        LOG.trace( "Method: createTableAndItsAdmin called." );


        LOG.trace( "Method: createTableAndItsAdmin finished." );
    }


    /**
     * @param tABLE_NAME
     */
    public void createAssociatedRolesAndPermission( String TABLE_NAME, String DATABASE )
    {
        LOG.trace( "Method: createAssociatedRolesAndPermission called." );

        TransactionalGraph odb = new OrientGraph( path, "admin", "admin" );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();

        //start creating admin role
        String role = OrientRole.ROLE_TABLE_ADMIN + "_" + TABLE_NAME;
        db.commit();
        OSecurity oSecurity = db.getMetadata().getSecurity();
        ORole restrictedRole = oSecurity.createRole( role, OSecurityRole.ALLOW_MODES.DENY_ALL_BUT );
        restrictedRole.addRule( ORule.ResourceGeneric.CLASS, TABLE_NAME, ORole.PERMISSION_CREATE + ORole.PERMISSION_UPDATE
            + ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.DATABASE, DATABASE, ORole.PERMISSION_CREATE + ORole.PERMISSION_UPDATE
            + ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.DATABASE, null, ORole.PERMISSION_CREATE + ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.SCHEMA, null, ORole.PERMISSION_CREATE + ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, null, ORole.PERMISSION_CREATE + ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, TABLE_NAME, ORole.PERMISSION_CREATE + ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.COMMAND, null, ORole.PERMISSION_CREATE + ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.COMMAND, TABLE_NAME, ORole.PERMISSION_CREATE + ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, OMetadataDefault.CLUSTER_INTERNAL_NAME, ORole.PERMISSION_CREATE
            + ORole.PERMISSION_READ + ORole.PERMISSION_UPDATE );
        restrictedRole.save();
        restrictedRole.reload();

        //start creating user role 
        role = OrientRole.ROLE_TABLE_USER + "_" + TABLE_NAME;
        // create role
        ORole restrictedRoleUser = oSecurity.createRole( role, OSecurityRole.ALLOW_MODES.DENY_ALL_BUT );
        restrictedRoleUser.addRule( ORule.ResourceGeneric.CLASS, TABLE_NAME, ORole.PERMISSION_CREATE );
        restrictedRoleUser.addRule( ORule.ResourceGeneric.DATABASE, DATABASE, ORole.PERMISSION_CREATE );
        restrictedRoleUser.addRule( ORule.ResourceGeneric.DATABASE, null, ORole.PERMISSION_READ );
        restrictedRoleUser.addRule( ORule.ResourceGeneric.SCHEMA, null, ORole.PERMISSION_READ );
        restrictedRoleUser.addRule( ORule.ResourceGeneric.CLUSTER, null, ORole.PERMISSION_READ );
        restrictedRoleUser.addRule( ORule.ResourceGeneric.CLUSTER, OMetadataDefault.CLUSTER_INTERNAL_NAME,
            ORole.PERMISSION_READ );
        restrictedRoleUser.save();
        restrictedRoleUser.reload();
        
        

        db.commit();
        db.close();
        LOG.trace( "Method: createAssociatedRolesAndPermission finished." );
    }

}
