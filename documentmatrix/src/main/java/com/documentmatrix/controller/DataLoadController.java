package com.documentmatrix.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.documentmatrix.metadata.FileObject;
/**
 * 
 * @author Vipin Kumar
 * @created 09-Apr-2015
 * 
 * This class is written for loading data to Cassandra and Hbase
 *
 */

@Controller
@RequestMapping("/load")
public class DataLoadController
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( DataLoadController.class );
    
    
    @RequestMapping(value="/data" ,method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public String loadData(@RequestBody FileObject fileObject)
    {
        LOG.info( "Loading data to cassandra" );
        
        return null;
    }
    
}
