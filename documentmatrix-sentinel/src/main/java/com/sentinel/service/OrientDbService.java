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
import com.sentinel.web.dto.OrientAdminRequest;
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


    @PreDestroy
    public void shutdown()
    {
        Orient.instance().shutdown();
    }


    /**
     * 
     */
    public void createTableAndItsAdmin(String TABLE_NAME,OrientAdminRequest orientAdminRequest)
    {
        LOG.trace("Method: createTableAndItsAdmin called.");
        
        
        LOG.trace("Method: createTableAndItsAdmin finished.");
    }


    /**
     * @param tABLE_NAME
     */
    public void createAssociatedRolesAndPermission( String tABLE_NAME )
    {
        LOG.trace("Method: createAssociatedRolesAndPermission called.");
        
        
        LOG.trace("Method: createAssociatedRolesAndPermission finished.");
    }

}
