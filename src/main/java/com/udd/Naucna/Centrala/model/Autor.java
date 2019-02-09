package com.udd.Naucna.Centrala.model;


import org.springframework.data.geo.Point;
import java.util.List;

import javax.persistence.Entity;
@Entity
public class Autor extends Korisnik{

	public Autor() {
		super();
	}

	public Autor(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			Point lokacija, List<Kupljeno> kupljeno, List<PretplataNaCasopis> pretplate) {
		super(id, korisnickoIme, lozinka, ime, prezime, email, lokacija, kupljeno, pretplate);
	}

	

}
