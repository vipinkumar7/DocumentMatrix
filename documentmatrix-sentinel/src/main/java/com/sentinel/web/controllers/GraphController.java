/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.web.controllers;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sentinel.persistence.models.User;


/**
 * @author Vipin Kumar
 * @created 28-Jan-2016
 * 
 * TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Controller
@RequestMapping ( value = "/graph")
public class GraphController
{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( GraphController.class );


    @RequestMapping ( value = "/{}")
    public String showRecords(@AuthenticationPrincipal User user)
    {
        return user.getEmail();
    }

}
