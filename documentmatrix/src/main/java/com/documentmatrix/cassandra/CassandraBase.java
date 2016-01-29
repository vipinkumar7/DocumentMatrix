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
 * @created 14-Apr-2015
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 *
 */
public abstract class CassandraBase {

	protected static Cluster cluster;
	protected static Keyspace keySpace;
	protected static Properties properties;
	
	protected void init()
	{
		cluster = HFactory.getOrCreateCluster("node1",
				"127.0.0.1:9160");
		ConfigurableConsistencyLevel ccl = new ConfigurableConsistencyLevel();
		ccl.setDefaultReadConsistencyLevel(HConsistencyLevel.ONE);
		keySpace = HFactory.createKeyspace("schemadb", cluster,
				ccl);


	}
}
