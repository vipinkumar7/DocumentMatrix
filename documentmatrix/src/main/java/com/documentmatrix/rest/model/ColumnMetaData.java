package com.documentmatrix.rest.model;

/**
 * 
 * @author Vipin Kumar
 * @created 14-Apr-2015
 * 
 * Column Metadata 
 *
 */
public class ColumnMetaData
{


    private final String label;
    private final String name;
    private final String columnFamily;
    private final String columnName;
    private final String dataType;


    ColumnMetaData( String label, String name, String columnFamily, String columnName, String dataType )
    {

        super();
        this.label = label;
        this.name = name;
        this.columnFamily = columnFamily;
        this.columnName = columnName;
        this.dataType = dataType;
    }


    public String getLabel()
    {
        return label;
    }


    public String getName()
    {
        return name;
    }


    public String getColumnFamily()
    {
        return columnFamily;
    }


    public String getColumnName()
    {
        return columnName;
    }


    public String getDataType()
    {
        return dataType;
    }

}
