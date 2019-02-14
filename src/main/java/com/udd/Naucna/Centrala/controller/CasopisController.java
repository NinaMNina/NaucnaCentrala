package com.udd.Naucna.Centrala.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udd.Naucna.Centrala.dto.CasopisDTO;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.services.CasopisService;
import com.udd.Naucna.Centrala.services.KorisnikService;
import com.udd.Naucna.Centrala.token.TokenUtils;

@Controller
@RequestMapping("/casopis")
public class CasopisController {
	@Autowired
	private CasopisService casopisService;
	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private KorisnikService korisnikService;
	
	@GetMapping(path = "/getAll", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<CasopisDTO>> getAll(){		
        return new ResponseEntity(casopisService.getAll(), HttpStatus.OK);
    }
	
	@PostMapping(path = "/odaberi/{token}", produces = "application/json", consumes = "application/json")
    public @ResponseBody ResponseEntity<Korisnik> odaberiCasopis(@RequestBody CasopisDTO casopisDTO, @PathVariable String token){	
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(null, HttpStatus.OK);
		}
		Korisnik k = korisnikService.findByKorisnickoIme(username);
		if(k==null){
			return new ResponseEntity(null, HttpStatus.OK);			
		}
		
        return new ResponseEntity(k, HttpStatus.OK);
    }
}
