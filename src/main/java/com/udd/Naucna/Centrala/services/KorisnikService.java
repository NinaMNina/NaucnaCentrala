package com.udd.Naucna.Centrala.services;

import java.util.ArrayList;

import com.udd.Naucna.Centrala.dto.FormFieldsCamundaDTO;
import com.udd.Naucna.Centrala.dto.KorisnikDTO;
import com.udd.Naucna.Centrala.dto.ZadaciDTO;
import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.model.Korisnik;

public interface KorisnikService {
	Korisnik uloguj(KorisnikDTO korisnik);

	Korisnik findByKorisnickoIme(String username);

	ArrayList<ZadaciDTO> getZadaci(Korisnik k);

	Korisnik registruj(Autor autor);
}
