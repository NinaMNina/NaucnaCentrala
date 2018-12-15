package com.udd.Naucna.Centrala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udd.Naucna.Centrala.services.ElasticSearchService;

@RestController
@RequestMapping(value = "/rest/")
public class ElasticSearchController {
	@Autowired
	private ElasticSearchService elasticSearchServices;
	
	@RequestMapping(value = "search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> completePayment(@RequestParam("paymentId") String paymentId){		
		return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
