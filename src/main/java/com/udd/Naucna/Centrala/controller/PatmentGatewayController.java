package com.udd.Naucna.Centrala.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udd.Naucna.Centrala.dto.TransakcijaIshodDTO;

@RestController
@RequestMapping(value = "/rest/")
public class PatmentGatewayController {
	@RequestMapping(value = "receiveResponse", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransakcijaIshodDTO> receiveResponse(@RequestBody TransakcijaIshodDTO ishod){		
		//TODO namestiti da registruje kupovinu
		System.out.println("BIO VIDO OTI'SO");
		return new ResponseEntity<TransakcijaIshodDTO>(ishod, HttpStatus.OK);
    }
}
