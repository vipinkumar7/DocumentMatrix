package com.documentmatrix.rest.model;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * @author Vipin Kumar
 * @created 14-Apr-2015
 * 
 * This class is written for returning response data response in a specified json format 
 *
 */
public class DataResponse implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    private List<ColumnMetaData> columnMetadata;

    private List<List<String>> columnData;


    public DataResponse( List<ColumnMetaData> columnMetadata, List<List<String>> columnData )
    {
        super();
        this.columnData = columnData;
        this.columnMetadata = columnMetadata;

    }


    public List<ColumnMetaData> getColumnMetadata()
    {
        return columnMetadata;
    }


    public void setColumnMetadata( List<ColumnMetaData> columnMetadata )
    {
        this.columnMetadata = columnMetadata;
    }


    public List<List<String>> getColumnData()
    {
        return columnData;
    }


    public void setColumnData( List<List<String>> columnData )
    {
        this.columnData = columnData;
    }


}
