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
package com.documentmatrix.factory;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import me.prettyprint.cassandra.dao.SimpleCassandraDao;
import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;


/**
 * @author "Swaraj Kumar"
 * @created 17-Apr-2015
 *
 * Factory Class for creating DAO for Keyspace and its column family.
 *
 */
public class CassandraDaoFactory
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( CassandraDaoFactory.class );

    private static final String COLUMN_FAMILY = ".columnfamily";

    @Autowired
    private Cluster cluster;

    @Autowired
    private ConfigurableConsistencyLevel consistencyLevelPolicy;

    @Autowired
    private Environment environment;

    @Value ( "${keyspace}")
    private String keyspaceNames;

    private Map<String, Map<String, SimpleCassandraDao>> keyspaceMap = new HashMap<String, Map<String, SimpleCassandraDao>>();;


    @PostConstruct
    private void initializeKeyspaces()
    {
        String[] keyspaces = keyspaceNames.split( "\\," );
        for ( String keyspaceStr : keyspaces ) {
            Keyspace keyspace = HFactory.createKeyspace( keyspaceStr, cluster, consistencyLevelPolicy );
            String columnFamilyNames = environment.getProperty( "${" + keyspaceStr + COLUMN_FAMILY + "}" );
            String[] columnFamilies = columnFamilyNames.split( "\\," );
            Map<String, SimpleCassandraDao> daoMap = new HashMap<String, SimpleCassandraDao>();
            for ( String columnFamily : columnFamilies ) {
                SimpleCassandraDao dao = new SimpleCassandraDao();
                dao.setKeyspace( keyspace );
                dao.setColumnFamilyName( columnFamily );
                daoMap.put( columnFamily, dao );
            }
            keyspaceMap.put( keyspaceStr, daoMap );
        }
    }


    /**
     * @return the keyspaceMap
     */
    public Map<String, Map<String, SimpleCassandraDao>> getKeyspaceMap()
    {
        return keyspaceMap;
    }

}
