package com.udd.Naucna.Centrala.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.springframework.data.geo.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Recenzent extends Korisnik{
	
	@ManyToMany
	@JsonIgnore
	private List<Casopis> angazovanje;
	
	@Column(nullable = true, length = 80)
	private String titula;
	
	@ManyToMany
	@JsonIgnore
	private List<NaucnaOblast> pokrivaNaucneOblasti;

	public List<Casopis> getAngazovanje() {
		return angazovanje;
	}

	public void setAngazovanje(List<Casopis> angazovanje) {
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

	public void setPokrivaNaucneOblasti(List<NaucnaOblast> pokrivaNaucneOblasti) {
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
		// TODO Auto-generated constructor stub
	}

	public Recenzent(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			Point lokacija, List<Kupljeno> kupljeno, List<PretplataNaCasopis> pretplate) {
		super(id, korisnickoIme, lozinka, ime, prezime, email, lokacija, kupljeno, pretplate);
		// TODO Auto-generated constructor stub
	}

	
	
}