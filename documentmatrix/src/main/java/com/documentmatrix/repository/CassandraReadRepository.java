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
package com.documentmatrix.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.documentmatrix.factory.CassandraDaoFactory;


/**
 * @author "Swaraj Kumar"
 * @created 16-Apr-2015
 *
 * Cassandra Repository for read operation.
 *
 */
@Repository
public class CassandraReadRepository
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( CassandraReadRepository.class );

    @Autowired
    CassandraDaoFactory cassandraDaoFactory;


    /**
     * @param columnName
     * @param value
     * @return
     */
    public String readByColumnAndValue( String columnName, String value )
    {
        /* LOG.trace( "Method: readByColumnAndValue called." );
         System.out.println( "DAO found" + columnFamilyDao.toString() );*/
        System.out.println( cassandraDaoFactory );
        return "flfkjkflfkl";
    }
}
