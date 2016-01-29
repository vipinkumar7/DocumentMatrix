package com.sentinel.graph.orientdb;

import java.util.Date;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
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
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;


/**
 * 
 * @author Vipin Kumar
 *
 */
public class TestGraph
{

    static String path = "remote:localhost/vipin";//"memory:db1";

    static String TABLE_NAME = "POST";
    static String DATABASE = "vipin";


    public static void main( String[] args )
    {

        //TransactionalGraph odb = new OrientGraph("remote:localhost/vipin","admin","admin");
        //ODatabaseDocumentTx db = ((OrientGraph) odb).getRawGraph();

        OrientGraphFactory graphFactory = new OrientGraphFactory( path );
        OrientGraph graph = graphFactory.getTx();
        ODatabaseDocumentTx db = ( (OrientGraph) graph ).getRawGraph();

        db.commit();
        createDummyRoleAndUser( db );
        db.close();

        System.out.println( "======================================" );
        db = new ODatabaseDocumentTx( path );
        db.open( "admin", "admin" );
        // Prints:
        // Hello 1
        // Hello 2
        ORecordIteratorClass<ODocument> i = db.browseClass( TABLE_NAME );

        for ( ODocument doc : db.browseClass( TABLE_NAME ) ) {

            System.out.println( doc.field( "Message" ) );
        }
        db.close();

        System.out.println( "======================================" );
        db = new ODatabaseDocumentTx( path );
        db.open( "user2", "user2" );
        // Prints:
        // Hello 2
        for ( ODocument doc : db.browseClass( TABLE_NAME ) ) {
            System.out.println( doc.field( "Message" ) );
        }
        //
        // PRINT ADMIN
        //
        db.close();
    }


    public static void createDummyRoleAndUser( ODatabaseDocumentTx db )
    {

        OSecurity oSecurity = db.getMetadata().getSecurity();

        // create role
        ORole restrictedRole = oSecurity.createRole( "foobar", OSecurityRole.ALLOW_MODES.DENY_ALL_BUT );
        restrictedRole.addRule( ORule.ResourceGeneric.CLASS, TABLE_NAME, ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.DATABASE, DATABASE, ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.DATABASE, null, ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.SCHEMA, null, ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, null, ORole.PERMISSION_READ );
        restrictedRole.addRule( ORule.ResourceGeneric.CLUSTER, OMetadataDefault.CLUSTER_INTERNAL_NAME, ORole.PERMISSION_READ );

        restrictedRole.save();
        restrictedRole.reload();

        OUser user = oSecurity.createUser( "user2", "user2", "foobar" );

        user.save();

        /*
         * OUser user = oSecurity.createUser("vipin", "vipin", new String[] {
         * "reader" }); user.save();
         */
        OClass restricted = db.getMetadata().getSchema().getClass( "ORestricted" );
        OClass docClass = db.getMetadata().getSchema().getOrCreateClass( TABLE_NAME, restricted );

        ODocument doc1 = new ODocument( docClass );
        ODocument doc2 = new ODocument( docClass );

        // The restricted record...
        doc1.field( "name", TABLE_NAME );
        doc1.field( "Id", 1, OType.INTEGER );
        doc1.field( "Message", "Hello 1", OType.STRING );
        doc1.save();

        // The unrestricted record...
        doc2.field( "name", TABLE_NAME );
        doc2.field( "Id", 2, OType.INTEGER );
        doc2.field( "Message", "Hello 2", OType.STRING );
        db.getMetadata().getSecurity().allowRole( doc2, OSecurityShared.ALLOW_READ_FIELD, "foobar" );
        doc2.save();
        db.commit();

    }


    private void populateData( OrientGraphFactory graphFactory )
    {
        OrientGraph graph = graphFactory.getTx();
        try {
            OrientVertex me = graph.addVertex( "class:Person", "name", "Moi" );
            OrientVertex you = graph.addVertex( "class:Person", "name", "Vous" );
            you.setProperty( "age", 1 );
            you.setProperty( "height", 2 );
            OrientEdge knows = graph.addEdge( "class:Knows", me, you, "Knows" );
            knows.setProperty( "since", new Date() );

            Vertex random = graph.addVertex( null, "name", "Someone" );

            graph.getRawGraph().getMetadata().getSecurity()
                .allowUser( me.getRecord(), OSecurityShared.ALLOW_ALL_FIELD, "report" );

            graph.commit();
        } finally {
            graph.shutdown();
        }
    }

}
