/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.util;

/**
 * @author Vipin Kumar
 * @created 28-Feb-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
public class GetEnums
{

    /**
     * common method to call all Enum class 
     * @param c
     * @param string
     * @return
     */
    public static <T extends Enum<T>> T getEnumFromString( Class<T> c, String string )
    {

        if ( c != null && string != null ) {
            try {
                return Enum.valueOf( c, string.trim().toUpperCase() );
            } catch ( IllegalArgumentException ie ) {

            }
        }
        return null;
    }
}
