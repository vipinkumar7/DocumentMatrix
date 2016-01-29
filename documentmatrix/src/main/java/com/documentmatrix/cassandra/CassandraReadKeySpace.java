package com.documentmatrix.cassandra;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

/**
 * 
 * @author Vipin Kumar
 * @created 14-Apr-2015
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 *
 */
public class CassandraReadKeySpace extends CassandraBase{

    
    public void readKeySpace()
    {
        int rowCount = 100;
        RangeSlicesQuery<String, String, String> rangeSlicesQuery1 = HFactory
                .createRangeSlicesQuery(keySpace, StringSerializer.get(),
                        StringSerializer.get(), StringSerializer.get())
                .setColumnFamily("cf")
                .setRange(null, null, false, 2).setRowCount(rowCount);
        String lastKey = null;
        // Query to iterate over all rows of cassandra Column Family
        rangeSlicesQuery1.setKeys(lastKey, null);
        QueryResult<OrderedRows<String, String, String>> result1 = rangeSlicesQuery1
                .execute();
        OrderedRows<String, String, String> rows1 = result1.get();
        System.out.println(rows1.getCount());
        for (Row<String, String, String> row : rows1) {
            String cassandra_key = row.getKey();
            System.out.println(cassandra_key.trim()+ " "+row.getColumnSlice().toString());
        }
    }
}
