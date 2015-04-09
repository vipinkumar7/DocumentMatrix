package com.documentmatrix.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Vipin Kumar
 * @created 09-Apr-2015
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 *
 */
public class FileObject
{

    @JsonProperty("location")
    private String location;


    public String getLocation()
    {
        return location;
    }


    public void setLocation( String location )
    {
        this.location = location;
    }


}
