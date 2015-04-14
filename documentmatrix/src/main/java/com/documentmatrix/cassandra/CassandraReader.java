package com.documentmatrix.cassandra;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

/**
 * 
 * @author Vipin Kumar
 * @created 15-Apr-2015
 * 
 * Class is written for all the read operation of cassandra
 *
 */
public class CassandraReader extends CassandraLoader
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( CassandraReader.class );


    public void read()
    {
        init();//Inititlize cluster
        RangeSlicesQuery<String, String, String> rangeQuery = HFactory.createRangeSlicesQuery( keyspace,
            StringSerializer.get(), StringSerializer.get(), StringSerializer.get() );
        rangeQuery.setColumnFamily( "as" );//TODO put your column family
        rangeQuery.setRange( null, null, false, 100 );
        rangeQuery.setKeys( null, null );
        rangeQuery.setRowCount( 100 );//Greater than count(*)
        QueryResult<OrderedRows<String, String, String>> result = rangeQuery.execute();
    }
}
