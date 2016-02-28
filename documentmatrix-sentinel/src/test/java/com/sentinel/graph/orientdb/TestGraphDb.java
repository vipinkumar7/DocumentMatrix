/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.graph.orientdb;

import java.util.List;

import org.junit.Test;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.OMetadataDefault;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.metadata.security.ORole;
import com.orientechnologies.orient.core.metadata.security.ORule;
import com.orientechnologies.orient.core.metadata.security.OSecurity;
import com.orientechnologies.orient.core.metadata.security.OSecurityRole;
import com.orientechnologies.orient.core.metadata.security.OSecurityShared;
import com.orientechnologies.orient.core.metadata.security.OUser;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;


/**
 * @author Vipin Kumar
 * @created 17-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class TestGraphDb
{

    //    mvn -Dtest=TestGraphDb#showDataForAllUsers test

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( TestGraphDb.class );

    final String path = "remote:localhost/vipin";

    final String TABLE_NAME = "POST";

    final String TABLE_NAME2 = "ACCOUNT";

    final String DATABASE = "vipin";

    final String[] dummyRole = { "admin_Post", "FooBar", "ROLE_ORIENT_TABLE_ADMIN_ACCOUNT" };
    final String[] dummyUsers = { "admin", "user1", "user2", "vipin@vipin.com" };


    @Test
    public void showDataForAllUsers()
    {
        for ( String user : dummyUsers ) {
            System.out.println( "Documents for user:" + user );
            TransactionalGraph odb = new OrientGraph( path, user, user );
            ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();
            for ( ODocument doc : db.browseClass( TABLE_NAME ) ) {

                System.out.println( doc.field( "Message" ) );
            }
            for ( ODocument doc : db.browseClass( TABLE_NAME2 ) ) {

                System.out.println( doc.field( "Message" ) );
            }
            db.close();
            System.out.println( "==============================================" );
        }
    }


    /**
     * 
     */
    @Test
    public void createNormalUserAndRoleForClass()
    {
        LOG.trace( "Method: createNormalUserAndRoleForClass called." );

        TransactionalGraph odb = new OrientGraph( path, dummyUsers[0], dummyUsers[0] );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();


        String role = dummyRole[1];

        db.commit();

        OSecurity oSecurity = db.getMetadata().getSecurity();
        // create role
        ORole restrictedRole = oSecurity.createRole( role, OSecurityRole.ALLOW_MODES.DENY_ALL_BUT );
        restrictedRole.addRule( ORule.ResourceGeneric.CLASS, TABLE_NAME, ORole.PERMISSION_CREATE );
        restrictedRole.addRule( ORule.ResourceGeneric.DATABASE, DATABASE, ORole.PERMISSION_CREATE );
        restrictedRole.addRule( ORule.ResourceGeneric.DATABASE, null, ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.SCHEMA, null, ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, null, ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, OMetadataDefault.CLUSTER_INTERNAL_NAME, ORole.PERMISSION_READ );
        restrictedRole.save();
        restrictedRole.reload();


        OUser user = oSecurity.createUser( dummyUsers[2], dummyUsers[2], role );
        user.save();
        db.commit();
        db.close();

        LOG.trace( "Method: createNormalUserAndRoleForClass finished." );
    }


    @Test
    public void createAdminRoleAndUserForClass()
    {
        LOG.trace( "Method: createAdminRoleAndUserForClass called." );

        TransactionalGraph odb = new OrientGraph( path, dummyUsers[0], dummyUsers[0] );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();


        String role = dummyRole[0];

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


        OUser user = oSecurity.createUser( dummyUsers[1], dummyUsers[1], role );
        user.save();
        db.commit();
        db.close();

        LOG.trace( "Method: createAdminRoleAndUserForClass finished." );
    }


    /**
     * 
     */
    @Test
    public void inserDataForAllUsers()
    {
        LOG.trace( "Method: inserDataForAllUsers called." );

        TransactionalGraph odb = new OrientGraph( path, dummyUsers[0], dummyUsers[0] );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();


        db.commit();
        OClass restricted = db.getMetadata().getSchema().getClass( "ORestricted" );
        OClass docClass = db.getMetadata().getSchema().getOrCreateClass( TABLE_NAME, restricted );

        ODocument doc1 = new ODocument( docClass );
        ODocument doc2 = new ODocument( docClass );

        // The restricted record...
        doc1.field( "name", TABLE_NAME );
        doc1.field( "Id", 1, OType.INTEGER );
        doc1.field( "Message", "admin document 1", OType.STRING );
        doc1.save();

        // The unrestricted record...
        doc2.field( "name", TABLE_NAME );
        doc2.field( "Id", 2, OType.INTEGER );
        doc2.field( "Message", "admin document 2", OType.STRING );
        db.getMetadata().getSecurity().allowRole( doc2, OSecurityShared.ALLOW_READ_FIELD, dummyRole[1] );
        doc2.save();
        db.commit();
        db.close();
        LOG.trace( "Method: inserDataForAllUsers finished." );


        db.open( dummyUsers[1], dummyUsers[1] );

        doc1 = new ODocument( docClass );
        doc2 = new ODocument( docClass );

        // The restricted record...
        doc1.field( "name", TABLE_NAME );
        doc1.field( "Id", 3, OType.INTEGER );
        doc1.field( "Message", "user1 document 1", OType.STRING );
        doc1.save();
        doc2.field( "name", TABLE_NAME );
        doc2.field( "Id", 4, OType.INTEGER );
        doc2.field( "Message", "user1 document 2", OType.STRING );
        doc2.save();

        db.commit();
        db.close();
    }


    @Test
    public void grantAccess()
    {
        TransactionalGraph odb = new OrientGraph( path, dummyUsers[0], dummyUsers[0] );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();
        List<ODocument> result = db.query( new OSQLSynchQuery<ODocument>( "select * from POST where Id=1" ) );
        for ( ODocument doc : result ) {
            db.getMetadata().getSecurity().allowRole( doc, OSecurityShared.ALLOW_READ_FIELD, dummyRole[1] );
            doc.save();
        }

        db.commit();
        db.close();

    }


    @Test
    public void inserAccountDataForUser()
    {
        TransactionalGraph odb = new OrientGraph( path, dummyUsers[3], dummyUsers[3] );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();


        db.commit();
        OClass restricted = db.getMetadata().getSchema().getClass( "ORestricted" );
        OClass docClass = db.getMetadata().getSchema().getOrCreateClass( TABLE_NAME2, restricted );

        ODocument doc1 = new ODocument( docClass );
        ODocument doc2 = new ODocument( docClass );

        // The restricted record...
        doc1.field( "name", TABLE_NAME );
        doc1.field( "Id", 1, OType.INTEGER );
        doc1.field( "account", "data", OType.STRING );
        doc1.field( "Message", "Account document 1", OType.STRING );
        doc1.save();

        // The unrestricted record...
        doc2.field( "name", TABLE_NAME );
        doc2.field( "Id", 2, OType.INTEGER );
        doc1.field( "account", "data", OType.STRING );
        doc2.field( "Message", "Account document 2", OType.STRING );
        db.getMetadata().getSecurity().allowRole( doc2, OSecurityShared.ALLOW_READ_FIELD, dummyRole[1] );
        doc2.save();
        db.commit();
        db.close();

    }
}
