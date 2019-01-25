package com.udd.Naucna.Centrala.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.dto.KorisnikDTO;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.repository.KorisnikRepository;
import com.udd.Naucna.Centrala.services.KorisnikService;
@Service
public class KorisnikServiceImpl implements KorisnikService{
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	
	@Override
	public Korisnik uloguj(KorisnikDTO korisnik) {
		Korisnik k0 = korisnikRepository.findByKorisnickoIme(korisnik.getIme());
		if(korisnik == null) {
	    	return null;
	    }
	    
	    if(!korisnik.getLozinka().equals(korisnik.getLozinka())) {
	    	return null;
	    }
		return k0;
	}
	

}
