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
import org.springframework.web.multipart.MultipartFile;

import com.udd.Naucna.Centrala.dto.RadDTO;
import com.udd.Naucna.Centrala.dto.RecenzentDTO;
import com.udd.Naucna.Centrala.dto.ZadaciDTO;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.Recenzent;
import com.udd.Naucna.Centrala.services.KorisnikService;
import com.udd.Naucna.Centrala.services.RadService;
import com.udd.Naucna.Centrala.services.RecenzentService;
import com.udd.Naucna.Centrala.token.TokenUtils;

@Controller
@RequestMapping("/udd")
public class UddController {
	@Autowired
	private KorisnikService korisnikService;
	@Autowired
	private RadService radService;
	@Autowired
	private RecenzentService recenzentService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@GetMapping(path = "/zadaci/{token}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<ZadaciDTO>> getZadaci(@PathVariable String token) {
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Korisnik k = korisnikService.findByKorisnickoIme(username);
		ArrayList<ZadaciDTO> retVal = korisnikService.getZadaci(k);
		return new ResponseEntity(retVal, HttpStatus.OK);
		
    }
	@GetMapping(path = "/upload/{token}", produces = "application/json")
    public @ResponseBody ResponseEntity<String> uploadPDFa(@PathVariable String token, @RequestBody MultipartFile file) {
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		String lokacija = radService.saveMultipartFile(file);
		return new ResponseEntity(lokacija, HttpStatus.OK);
		
    }
	@PostMapping(path = "/rad/{token}", produces = "application/json", consumes="application/json")
    public @ResponseBody ResponseEntity<RadDTO> getRad(@PathVariable String token, @RequestBody ZadaciDTO zad) {
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		RadDTO retVal = radService.getRadDTO(zad.getRad());
		return new ResponseEntity(retVal, HttpStatus.OK);
		
    }
	@PostMapping(path = "/recenzenti/{token}", produces = "application/json", consumes="application/json")
    public @ResponseBody ResponseEntity<ArrayList<RecenzentDTO>> getRecenzenti(@PathVariable String token, @RequestBody ZadaciDTO zad) {
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		ArrayList<RecenzentDTO> retVal = recenzentService.getRecenzenti(zad);
		return new ResponseEntity(retVal, HttpStatus.OK);
		
    }
	@PostMapping(path = "/recenzentiStrucni/{token}", produces = "application/json", consumes="application/json")
    public @ResponseBody ResponseEntity<ArrayList<RecenzentDTO>> getStrucniRecenzenti(@PathVariable String token, @RequestBody ZadaciDTO zad) {
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		ArrayList<RecenzentDTO> retVal = recenzentService.getStrucniRecenzenti(zad);
		return new ResponseEntity(retVal, HttpStatus.OK);
		
    }
	@PostMapping(path = "/recenzentiUdaljeni/{token}", produces = "application/json", consumes="application/json")
    public @ResponseBody ResponseEntity<ArrayList<RecenzentDTO>> getUdaljeniRecenzenti(@PathVariable String token, @RequestBody ZadaciDTO zad) {
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		ArrayList<RecenzentDTO> retVal = recenzentService.getUdaljeniRecenzenti(zad);
		return new ResponseEntity(retVal, HttpStatus.OK);
		
    }
	@PostMapping(path = "/dodajRecenzente/{id}/{token}", produces = "application/json", consumes="application/json")
    public @ResponseBody ResponseEntity<Boolean> dodajRecenzente(@PathVariable Long id, @PathVariable String token, @RequestBody ArrayList<Long> odabrani) {
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Boolean retVal = radService.dodajRecenzente(id, odabrani);
		return new ResponseEntity(retVal, HttpStatus.OK);
		
    }
}
