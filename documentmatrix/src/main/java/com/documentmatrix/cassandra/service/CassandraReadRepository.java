/**
 *  * Copyright (c) 2015 DocumentMatrix.
 * All rights reserved.
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 *
 */
package com.documentmatrix.cassandra.service;

import me.prettyprint.cassandra.dao.SimpleCassandraDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author "Swaraj Kumar"
 * @created 16-Apr-2015
 *
 * TODO: Write a quick description of what the class is supposed to do.
 *
 */
@Repository
public class CassandraReadRepository
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( CassandraReadRepository.class );

    @Autowired
    SimpleCassandraDao columnFamilyDao;


    /**
     * @param columnName
     * @param value
     * @return
     */
    public String readByColumnAndValue( String columnName, String value )
    {
        LOG.trace( "Method: readByColumnAndValue called." );
        System.out.println( "DAO found" + columnFamilyDao.toString() );
        return "flfkjkflfkl";
    }
}
