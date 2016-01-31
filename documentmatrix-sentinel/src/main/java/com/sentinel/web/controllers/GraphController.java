/**
 *  * Copyright (c) 2016 :;;. 
 * All rights reserved. 
 * 
 *Licensed under the Beer License

 */
package com.sentinel.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.orientechnologies.orient.core.Orient;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.sentinel.persistence.models.User;
import com.sentinel.persistence.repository.UserRepository;
import com.sentinel.service.OrientDbService;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

/**
 * @author Vipin Kumar
 * @created 28-Jan-2016
 * 
 *          TODO: Write a quick description of what the class is supposed to do.
 * 
 */
@Controller
@RequestMapping(value = "/graph")
public class GraphController {

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(GraphController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;// use StandardPBEStringEncryptor

	@Autowired
	OrientDbService orientdbService;

	@RequestMapping(value = "/show/{TABLE_NAME}", method = RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	public String showRecords(@PathVariable String TABLE_NAME)// TODO
																// //@AuthenticationPrincipal
																// User user1
	{

		final String user = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		orientdbService.getData(TABLE_NAME);
		User u = userRepository.findByEmail(user);
		System.out.println(user + "  " + u);
		return "hello";
	}

	@RequestMapping(value = "/grant", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	public void giveAccess() {

	}

}
