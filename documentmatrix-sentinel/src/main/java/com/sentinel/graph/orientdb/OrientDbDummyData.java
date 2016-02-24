package com.sentinel.graph.orientdb;

import java.util.List;

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
 * 
 * @author Vipin Kumar
 *
 */
public class OrientDbDummyData
{
    String path = "remote:localhost/vipin";// "memory:db1";

    String TABLE_NAME = "POST";
    String DATABASE = "vipin";


    public static void main( String[] args )
    {


        new OrientDbDummyData().showForUser( "user2" );//inserDumyDataasUser();
        //new OrientDbDummyData().grantAccess( "admin", "FooBar1" );
    }


    public void showForUser( String user )
    {
        TransactionalGraph odb = new OrientGraph( "remote:localhost/vipin", user, user );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();
        for ( ODocument doc : db.browseClass( TABLE_NAME ) ) {
            System.out.println( doc.field( "Message" ) );
        }
        db.close();

    }


    public void inserDumyDataasUser()
    {

        String userd = "user2";
        TransactionalGraph odb = new OrientGraph( "remote:localhost/vipin", "admin", "admin" );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();


        String role = "admin_Post";

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
        restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, OMetadataDefault.CLUSTER_INTERNAL_NAME, ORole.PERMISSION_CREATE
            + ORole.PERMISSION_READ + ORole.PERMISSION_UPDATE );
        restrictedRole.save();
        restrictedRole.reload();


        OUser user = oSecurity.createUser( userd, userd, "admin_Post" );
        user.save();
        db.commit();
        db.close();

        db.open( userd, userd );

        OClass restricted = db.getMetadata().getSchema().getClass( "ORestricted" );
        OClass docClass = db.getMetadata().getSchema().getOrCreateClass( TABLE_NAME, restricted );

        ODocument doc1 = new ODocument( docClass );

        // The restricted record...
        doc1.field( "name", TABLE_NAME );
        doc1.field( "Id", 3, OType.INTEGER );
        doc1.field( "Message", "user3 document 1", OType.STRING );
        doc1.save();
        db.commit();
        db.close();
    }


    public void start()
    {

        TransactionalGraph odb = new OrientGraph( "remote:localhost/vipin", "admin", "admin" );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();

        String dummyRole = "FooBar1";
        String[] dummyUsers = { "user1" };

        /*
         * OrientGraphFactory graphFactory = new OrientGraphFactory( path );
         * OrientGraph graph = graphFactory.getTx(); ODatabaseDocumentTx db = (
         * (OrientGraph) graph ).getRawGraph();
         */
        db.commit();

        // create a dummy role
        createDummyRole( db, dummyRole );
        createDummyUser( db, dummyUsers, dummyRole );

        insertDummyData( db, dummyUsers, dummyRole );
        System.out.println( "======================================" );

        db = new ODatabaseDocumentTx( path );
        db.open( dummyUsers[0], dummyUsers[0] );

        for ( ODocument doc : db.browseClass( TABLE_NAME ) ) {
            System.out.println( doc.field( "Message" ) );
        }
        db.close();

        System.out.println( "======================================" );
        db = new ODatabaseDocumentTx( path );

        db.open( "admin", "admin" );
        for ( ODocument doc : db.browseClass( TABLE_NAME ) ) {
            System.out.println( doc.field( "Message" ) );
        }

        db.close();
    }


    public void createDummyRole( ODatabaseDocumentTx db, String role )
    {

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

    }


    public void createDummyUser( ODatabaseDocumentTx db, String[] users, String role )
    {
        OSecurity oSecurity = db.getMetadata().getSecurity();
        for ( String en : users ) {
            OUser user = oSecurity.createUser( en, en, role );
            user.save();
        }
    }


    public void insertDummyData( ODatabaseDocumentTx db, String[] dummyUsers, String dummyRole )
    {

        OClass restricted = db.getMetadata().getSchema().getClass( "ORestricted" );
        OClass docClass = db.getMetadata().getSchema().getOrCreateClass( TABLE_NAME, restricted );

        ODocument doc1 = new ODocument( docClass );
        ODocument doc2 = new ODocument( docClass );

        // The restricted record...
        doc1.field( "name", TABLE_NAME );
        doc1.field( "Id", 1, OType.INTEGER );
        doc1.field( "Message", "user1 document 1", OType.STRING );
        doc1.save();

        // The unrestricted record...
        doc2.field( "name", TABLE_NAME );
        doc2.field( "Id", 2, OType.INTEGER );
        doc2.field( "Message", "user1 document 2", OType.STRING );
        db.getMetadata().getSecurity().allowRole( doc2, OSecurityShared.ALLOW_READ_FIELD, dummyRole );
        doc2.save();
        db.commit();
        db.close();
    }


    public void grantAccess( String user, String dummyRole )
    {
        TransactionalGraph odb = new OrientGraph( "remote:localhost/vipin", user, user );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();
        List<ODocument> result = db.query( new OSQLSynchQuery<ODocument>( "select * from POST where Id=1" ) );
        for ( ODocument doc : result ) {
            db.getMetadata().getSecurity().allowRole( doc, OSecurityShared.ALLOW_READ_FIELD, dummyRole );
        }
    }

}
