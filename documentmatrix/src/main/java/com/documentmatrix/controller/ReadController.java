package com.documentmatrix.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.documentmatrix.cassandra.service.CassandraReadRepository;


/**
 *
 * @author Vipin Kumar
 * @created 09-Apr-2015
 *
 * This Class is written for Reading Data from cassandra and hbase
 *
 */
@RestController
public class ReadController
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( ReadController.class );

    @Autowired
    CassandraReadRepository readRepository;


    @RequestMapping ( value = "/data", method = RequestMethod.GET)
    public String readData( @RequestParam ( "column") String columnName, @RequestParam ( "value") String value )
    {
        LOG.debug( "DAO is accessing" + readRepository.readByColumnAndValue( columnName, value ) );
        return null;
    }

}
