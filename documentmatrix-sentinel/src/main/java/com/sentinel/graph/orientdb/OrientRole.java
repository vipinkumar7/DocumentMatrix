/**
 *  * Copyright (c) 2016 ;; 
 * All rights reserved. 
 * 
 *	Licensed under Beer License
 */
package com.sentinel.graph.orientdb;

/**
 * @author Vipin Kumar
 * @created 22-Feb-2016
 * <p>
 * Orient DB standard roles which needs to be appended 
 * as prefix to TABLE_NAME
 *  
 * </p>
 */
public enum OrientRole
{

    /* 
     *  [ create new role for each table] 
     *   String TABLE_NAME="POST";
       String baseRole=OrientRole.ROLE_TABLE_ADMIN.toString();
       String role=baseRole+"_"+TABLE_NAME;
       System.out.println(role);
     * */

    ROLE_TABLE_ADMIN, ROLE_TABLE_USER
}
