package com.udd.Naucna.Centrala.repository;

import com.udd.Naucna.Centrala.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{

	public Korisnik findByKorisnickoIme(String ime);

}
