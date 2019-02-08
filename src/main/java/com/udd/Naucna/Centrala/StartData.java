package com.udd.Naucna.Centrala;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.repository.AutorRepository;
import com.udd.Naucna.Centrala.repository.KorisnikRepository;

@Component
public class StartData {
	@Autowired
    private KorisnikRepository korisnikRepository;
	@Autowired
    private AutorRepository autorRepository;
	
	@PostConstruct
    private void init(){
		Autor a0 = new Autor(null, "nina.m", "nina", "Nina", "Miladinovic", "n@n.com", "Novi Sad", "Srbija", new ArrayList<>(),new ArrayList<>());
		a0 = autorRepository.save(a0);
	}
}
