/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.orientechnologies.orient.core.Orient;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.security.OSecurityShared;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;


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


    @PostConstruct
    public void init()
    {
        LOG.debug( "Orient shutdownhook " );
        Orient.instance().removeShutdownHook();
    }


    public String getData( String TABLE_NAME, String username )
    {
        OrientGraphFactory graphFactory = new OrientGraphFactory( path, username, username );
        OrientGraph graph = graphFactory.getTx();
        ODatabaseDocumentTx db = ( (OrientGraph) graph ).getRawGraph();
        StringBuilder builder = new StringBuilder();
        for ( ODocument doc : db.browseClass( TABLE_NAME ) ) {
            builder.append( doc.field( "Message" ) );
            builder.append( "\n" );
        }
        return builder.toString();
    }


    public void grantAccess( String user )
    {
        TransactionalGraph odb = new OrientGraph( "remote:localhost/vipin", user, user );
        ODatabaseDocumentTx db = ( (OrientGraph) odb ).getRawGraph();
        List<ODocument> result = db.query( new OSQLSynchQuery<ODocument>( "select * from POST where Id=1" ) );
        for ( ODocument doc : result ) {
            db.getMetadata().getSecurity().allowRole( doc, OSecurityShared.ALLOW_READ_FIELD, "FooBar1" );
        }
    }


    @PreDestroy
    public void shutdown()
    {
        Orient.instance().shutdown();
    }

}
