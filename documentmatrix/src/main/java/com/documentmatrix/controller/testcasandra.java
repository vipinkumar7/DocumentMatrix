package com.documentmatrix.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

public class testcasandra {
	protected static Cluster tutorialCluster;
	protected static Keyspace tutorialKeyspace;
	protected static Properties properties;

	public static void main(String as[]) {

		// To modify the default ConsistencyLevel of QUORUM, create a
		// me.prettyprint.hector.api.ConsistencyLevelPolicy and use the
		// overloaded form:
		// tutorialKeyspace = HFactory.createKeyspace("Tutorial",
		// tutorialCluster, consistencyLevelPolicy);
		// see also
		// me.prettyprint.cassandra.model.ConfigurableConsistencyLevelPolicy[Test]
		// for details

		tutorialCluster = HFactory.getOrCreateCluster("demo_1node",
				"127.0.0.1:9160");
		ConfigurableConsistencyLevel ccl = new ConfigurableConsistencyLevel();
		ccl.setDefaultReadConsistencyLevel(HConsistencyLevel.ONE);
		tutorialKeyspace = HFactory.createKeyspace("musicdb", tutorialCluster,
				ccl);

		System.out.println(tutorialCluster.describeClusterName());

		/*
		 * SliceQuery<String, Composite, String> sliceQuery = HFactory
		 * .createSliceQuery(tutorialKeyspace, StringSerializer.get(), new
		 * CompositeSerializer(), StringSerializer.get());
		 * sliceQuery.setColumnFamily("album"); sliceQuery.setKey("ALL");
		 */

		/*
		 * SliceQuery<String, String, String> query =
		 * HFactory.createSliceQuery(tutorialKeyspace, StringSerializer.get(),
		 * StringSerializer.get(), StringSerializer.get());
		 * query.setColumnFamily("album"); query.setKey("ALL");
		 * query.setRange(null, null, false, Integer.MAX_VALUE);
		 */

		// ColumnSliceIterator<String, String, String> iterator = new
		// ColumnSliceIterator<String, String, String>(query, null, "\uFFFF",
		// false);

		// QueryResult<ColumnSlice<String,String>> result = query.execute();

		/*
		 * while (iterator.hasNext()) { HColumnImpl<String, String> column =
		 * (HColumnImpl<String, String>) iterator.next();
		 * System.out.println("Column name = " + column.getName() +
		 * "; Column value = " + column.getValue());
		 * 
		 * }
		 */
		
		
		
		int rowCount = 3197;
	    RangeSlicesQuery<String, String, String> rangeSlicesQuery1 = HFactory
	            .createRangeSlicesQuery(tutorialKeyspace, StringSerializer.get(),
	            		StringSerializer.get(), StringSerializer.get())
	            .setColumnFamily("album")
	            .setRange(null, null, false, rowCount).setRowCount(rowCount);
	    String lastKey = null;
	    // Query to iterate over all rows of cassandra Column Family
	    rangeSlicesQuery1.setKeys(lastKey, null);
	    QueryResult<OrderedRows<String, String, String>> result1 = rangeSlicesQuery1
	            .execute();
	    OrderedRows<String, String, String> rows1 = result1.get();
	    System.out.println(rows1.getCount());
	    Iterator<Row<String, String, String>> rowsIterator = rows1.iterator();
	    
	    while (rowsIterator.hasNext()) {
	        Row<String, String, String> row = rowsIterator.next();
	        if (row.getColumnSlice().getColumns().isEmpty()) {
                continue;
              }
	        System.out.print(row.getKey());
	    }

	    /*for (Row<String, String, String> row : rows1) {
	        String cassandra_key = row.getKey();
	       List<HColumn<String, String>> p= row.getColumnSlice().getColumns();
	    }*/
	    
/*	    System.exit(0);
		int row_count = 2;

		RangeSlicesQuery<UUID, String, Long> rangeSlicesQuery = HFactory
				.createRangeSlicesQuery(tutorialKeyspace, UUIDSerializer.get(),
						StringSerializer.get(), LongSerializer.get())
				.setColumnFamily("album").setRange(null, null, false, 2)
				.setRowCount(2);

		UUID last_key = null;

		while (true) {
			rangeSlicesQuery.setKeys(last_key, null);
			System.out.println(" > " + last_key);

			QueryResult<OrderedRows<UUID, String, Long>> result = rangeSlicesQuery
					.execute();
			OrderedRows<UUID, String, Long> rows = result.get();
			Iterator<Row<UUID, String, Long>> rowsIterator = rows.iterator();
*/
			// we'll skip this first one, since it is the same as the last one
			// from previous time we executed
/*			if (last_key != null && rowsIterator != null)
				rowsIterator.next();

			while (rowsIterator.hasNext()) {
				Row<UUID, String, Long> row = rowsIterator.next();
				last_key = row.getKey();

				if (row.getColumnSlice().getColumns().isEmpty()) {
					continue;
				}

				System.out.println(row);
			}

			if (rows.getCount() < row_count)
				break;
		}
*/
	}
}
