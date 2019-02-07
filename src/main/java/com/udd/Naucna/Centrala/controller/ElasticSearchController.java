package com.udd.Naucna.Centrala.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udd.Naucna.Centrala.dto.HighlightedRadDTO;
import com.udd.Naucna.Centrala.dto.ParametriDTO;
import com.udd.Naucna.Centrala.dto.RadDTO;
import com.udd.Naucna.Centrala.services.ElasticSearchService;
import com.udd.Naucna.Centrala.services.RadService;

@RestController
@RequestMapping(value = "/rest/")
public class ElasticSearchController {
	@Autowired
	private ElasticSearchService elasticSearchServices;
	
	@Autowired
	private RadService radService;
	
	@RequestMapping(value = "indexing", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RadDTO> indexing(@RequestBody RadDTO rad){		
		RadDTO retVal = elasticSearchServices.uploadRad(rad);
		return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
	
	@RequestMapping(value = "search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HighlightedRadDTO>> searchParametri(@RequestBody ParametriDTO parametri){		
		ArrayList<HighlightedRadDTO> retVal = elasticSearchServices.searchParams(parametri);
		return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
	@RequestMapping(value = "search/{tekst}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HighlightedRadDTO>> searchObican(@PathVariable String tekst){	
		ArrayList<HighlightedRadDTO> retVal = elasticSearchServices.searchObican(tekst);
		return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
	@RequestMapping(value = "moreLikeThis/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RadDTO>> moreLikeThis(@PathVariable Long id){
		if(!radService.exists(id)){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ArrayList<RadDTO> retVal = elasticSearchServices.moreLikeThis(id);
		return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
}
