package com.udd.Naucna.Centrala.services;

import com.udd.Naucna.Centrala.dto.KorisnikDTO;
import com.udd.Naucna.Centrala.model.Korisnik;

public interface KorisnikService {
	Korisnik uloguj(KorisnikDTO korisnik);
}