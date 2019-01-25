package com.udd.Naucna.Centrala.services.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.dto.FormFieldsCamunda;
import com.udd.Naucna.Centrala.dto.KorisnikDTO;
import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.enums.TipKorisnika;
import com.udd.Naucna.Centrala.repository.AutorRepository;
import com.udd.Naucna.Centrala.repository.KorisnikRepository;
import com.udd.Naucna.Centrala.services.KorisnikService;
@Service
public class KorisnikServiceImpl implements KorisnikService{
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private AutorRepository autorRepository;
	
	
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


	@Override
	public Korisnik registruj(FormFieldsCamunda ffc, KorisnikDTO korisnik) {
		Autor a = new Autor(ffc.getFormFields().get(0).getValue().toString(),
				ffc.getFormFields().get(1).getValue().toString(),
				ffc.getFormFields().get(2).getValue().toString(),
				ffc.getFormFields().get(3).getValue().toString(),
				ffc.getFormFields().get(4).getValue().toString());
		autorRepository.save(a);
		Korisnik k = new Korisnik(korisnik.getIme(), korisnik.getLozinka(), TipKorisnika.OBICAN, new ArrayList<>(), new ArrayList<>(),
				a, null, null, null);
		return korisnikRepository.save(k);
	}
	

}
