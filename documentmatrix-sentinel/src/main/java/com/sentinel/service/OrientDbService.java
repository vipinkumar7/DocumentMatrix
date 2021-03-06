/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.orientechnologies.orient.core.Orient;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.OMetadataDefault;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.security.ORole;
import com.orientechnologies.orient.core.metadata.security.ORule;
import com.orientechnologies.orient.core.metadata.security.OSecurity;
import com.orientechnologies.orient.core.metadata.security.OSecurityRole;
import com.orientechnologies.orient.core.metadata.security.OUser;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.sentinel.commons.CommonConstants;
import com.sentinel.graph.orientdb.OrientRole;
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

    @Autowired
    private Environment env;

    private final String path = "remote:localhost/vipin";


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
     *TODO create single pool for db connections
     */
    public void grantUserRole( String newUser, String role )
    {
        LOG.trace( "Method: createTableAndItsAdmin called." );
        TransactionalGraph odb = new OrientGraph( path, env.getProperty( CommonConstants.ORIENT_ADMIN ),
            env.getProperty( CommonConstants.ORIENT_PASSWORD ) );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();
        db.commit();
        OSecurity oSecurity = db.getMetadata().getSecurity();
        OUser user = oSecurity.createUser( newUser, newUser, role );
        user.save();
        db.commit();
        db.close();
        LOG.trace( "Method: createTableAndItsAdmin finished." );
    }


    /**
     * 
     * @param TABLE_NAME
     * @param DATABASE
     * @return 
     */
    public Map<String, Set<String>> createAssociatedRolesAndPermission( String TABLE_NAME, String DATABASE )
    {
        LOG.trace( "Method: createAssociatedRolesAndPermission called." );

        Map<String, Set<String>> rolesandPerm = new HashMap<String, Set<String>>();
        TransactionalGraph odb = new OrientGraph( path, "admin", "admin" );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();

        //start creating admin role
        String role = OrientRole.ROLE_ORIENT_TABLE_ADMIN + "_" + TABLE_NAME;
        db.commit();
        OSecurity oSecurity = db.getMetadata().getSecurity();
        createRole( OrientRole.ROLE_ORIENT_TABLE_ADMIN, role, oSecurity, TABLE_NAME, DATABASE );
        rolesandPerm.put( role, null );

        //start creating user role 
        role = OrientRole.ROLE_ORIENT_TABLE_USER + "_" + TABLE_NAME;
        // create role
        createRole( OrientRole.ROLE_ORIENT_TABLE_USER, role, oSecurity, TABLE_NAME, DATABASE );
        rolesandPerm.put( role, null );
        db.commit();
        db.close();
        LOG.trace( "Method: createAssociatedRolesAndPermission finished." );
        return rolesandPerm;
    }


    public boolean checkForTableinOrient( String TABLE_NAME )
    {
        TransactionalGraph odb = new OrientGraph( path, "admin", "admin" );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();
        db.commit();
        OClass docClass = db.getMetadata().getSchema().getClass( TABLE_NAME );
        db.commit();
        db.close();
        return docClass != null;
    }


    public boolean checkForUserInOrient( String USER )
    {
        TransactionalGraph odb = new OrientGraph( path, "admin", "admin" );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();
        List<ODocument> result = db.query( new OSQLSynchQuery<ODocument>( "select * from OUser where name='" + USER + "'" ) );
        db.close();
        return result != null;

    }


    public boolean checkForRoleInOrient( String ROLE )
    {
        TransactionalGraph odb = new OrientGraph( path, "admin", "admin" );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();
        List<ODocument> result = db.query( new OSQLSynchQuery<ODocument>( "select * from ORole where name='" + ROLE + "'" ) );
        db.close();
        return result != null;

    }


    public void createRole( OrientRole orientRole, String actualRole, OSecurity oSecurity, String TABLE_NAME,
        String DATABASE_NAME )
    {

        switch ( orientRole ) {

            case ROLE_ORIENT_TABLE_ADMIN:
                ORole restrictedRole = oSecurity.createRole( actualRole, OSecurityRole.ALLOW_MODES.DENY_ALL_BUT );
                restrictedRole.addRule( ORule.ResourceGeneric.CLASS, TABLE_NAME, ORole.PERMISSION_CREATE
                    + ORole.PERMISSION_UPDATE + ORole.PERMISSION_READ );
                restrictedRole.addRule( ORule.ResourceGeneric.DATABASE, DATABASE_NAME, ORole.PERMISSION_CREATE
                    + ORole.PERMISSION_UPDATE + ORole.PERMISSION_READ );
                restrictedRole.addRule( ORule.ResourceGeneric.DATABASE, null, ORole.PERMISSION_CREATE + ORole.PERMISSION_READ );
                restrictedRole.addRule( ORule.ResourceGeneric.SCHEMA, null, ORole.PERMISSION_CREATE + ORole.PERMISSION_READ );
                restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, null, ORole.PERMISSION_CREATE + ORole.PERMISSION_READ );
                restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, TABLE_NAME, ORole.PERMISSION_CREATE
                    + ORole.PERMISSION_READ );
                restrictedRole.addRule( ORule.ResourceGeneric.COMMAND, null, ORole.PERMISSION_CREATE + ORole.PERMISSION_READ );
                restrictedRole.addRule( ORule.ResourceGeneric.COMMAND, TABLE_NAME, ORole.PERMISSION_CREATE
                    + ORole.PERMISSION_READ );
                restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, OMetadataDefault.CLUSTER_INTERNAL_NAME,
                    ORole.PERMISSION_CREATE + ORole.PERMISSION_READ + ORole.PERMISSION_UPDATE );
                restrictedRole.save();
                restrictedRole.reload();
                break;
            case ROLE_ORIENT_TABLE_USER:
                ORole restrictedRoleUser = oSecurity.createRole( actualRole, OSecurityRole.ALLOW_MODES.DENY_ALL_BUT );
                restrictedRoleUser.addRule( ORule.ResourceGeneric.CLASS, TABLE_NAME, ORole.PERMISSION_CREATE );
                restrictedRoleUser.addRule( ORule.ResourceGeneric.DATABASE, DATABASE_NAME, ORole.PERMISSION_CREATE );
                restrictedRoleUser.addRule( ORule.ResourceGeneric.DATABASE, null, ORole.PERMISSION_READ );
                restrictedRoleUser.addRule( ORule.ResourceGeneric.SCHEMA, null, ORole.PERMISSION_READ );
                restrictedRoleUser.addRule( ORule.ResourceGeneric.CLUSTER, null, ORole.PERMISSION_READ );
                restrictedRoleUser.addRule( ORule.ResourceGeneric.CLUSTER, OMetadataDefault.CLUSTER_INTERNAL_NAME,
                    ORole.PERMISSION_READ );
                restrictedRoleUser.save();
                restrictedRoleUser.reload();
                break;
            default:
                break;
        }


    }
}
