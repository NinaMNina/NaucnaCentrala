package com.udd.Naucna.Centrala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udd.Naucna.Centrala.dto.RadDTO;
import com.udd.Naucna.Centrala.services.ElasticSearchService;

@RestController
@RequestMapping(value = "/rest/")
public class ElasticSearchController {
	@Autowired
	private ElasticSearchService elasticSearchServices;
	
	@RequestMapping(value = "indexing", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RadDTO> completePayment(@RequestBody RadDTO rad){		
		RadDTO retVal = elasticSearchServices.uploadRad(rad);
		return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
}
