package com.udd.Naucna.Centrala.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.data.geo.Point;

@Entity
public class Recenzent extends Korisnik{
	
	@ManyToMany
	private List<Casopis> angazovanje;
	
	@Column(nullable = true, length = 80)
	private String titula;
	
	@OneToMany
	private List<NaucnaOblast> pokrivaNaucneOblasti;

	public void setAngazovanje(List<Casopis> angazovanje) {
		this.angazovanje = angazovanje;
	}

	public void setPokrivaNaucneOblasti(List<NaucnaOblast> pokrivaNaucneOblasti) {
		this.pokrivaNaucneOblasti = pokrivaNaucneOblasti;
	}

	public List<Casopis> getAngazovanje() {
		return angazovanje;
	}

	public void setAngazovanje(ArrayList<Casopis> angazovanje) {
		this.angazovanje = angazovanje;
	}

	public String getTitula() {
		return titula;
	}

	public void setTitula(String titula) {
		this.titula = titula;
	}

	public List<NaucnaOblast> getPokrivaNaucneOblasti() {
		return pokrivaNaucneOblasti;
	}

	public void setPokrivaNaucneOblasti(ArrayList<NaucnaOblast> pokrivaNaucneOblasti) {
		this.pokrivaNaucneOblasti = pokrivaNaucneOblasti;
	}


	public Recenzent(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			Point lokacija, List<Kupljeno> kupljeno, List<PretplataNaCasopis> pretplate, List<Casopis> angazovanje,
			String titula, List<NaucnaOblast> pokrivaNaucneOblasti) {
		super(id, korisnickoIme, lozinka, ime, prezime, email, lokacija, kupljeno, pretplate);
		this.angazovanje = angazovanje;
		this.titula = titula;
		this.pokrivaNaucneOblasti = pokrivaNaucneOblasti;
	}

	public Recenzent() {
		super();
	}

	
	
	
}