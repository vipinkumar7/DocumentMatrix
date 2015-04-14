package com.documentmatrix.cassandra;

import java.util.Properties;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

/**
 * 
 * @author Vipin Kumar
 * @created 15-Apr-2015
 * 
 * This class is to load all cassandra Indtances
 *
 */
public abstract class CassandraLoader
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( CassandraLoader.class );
    
    protected static Cluster cluster;
    protected static Keyspace keyspace;

    public void init()
    {
        cluster = HFactory.getOrCreateCluster("demo","10.73.44.121:9160");
            ConfigurableConsistencyLevel ccl = new ConfigurableConsistencyLevel();
            ccl.setDefaultReadConsistencyLevel(HConsistencyLevel.ONE);
            keyspace = HFactory.createKeyspace("dd", cluster, ccl);
            
            }

    
}
