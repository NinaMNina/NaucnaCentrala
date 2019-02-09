package com.udd.Naucna.Centrala.model;


import org.springframework.data.geo.Point;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Urednik extends Korisnik{
	
	@OneToOne
	private Casopis casopis;
	
	@Column(nullable = true, length = 80)
	private String titula;


	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}


	public String getTitula() {
		return titula;
	}

	public void setTitula(String titula) {
		this.titula = titula;
	}



	public Urednik(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			Point lokacija, List<Kupljeno> kupljeno, List<PretplataNaCasopis> pretplate, Casopis casopis,
			String titula) {
		super(id, korisnickoIme, lozinka, ime, prezime, email, lokacija, kupljeno, pretplate);
		this.casopis = casopis;
		this.titula = titula;
	}

	public Urednik() {
		super();
	}

	
	
}
