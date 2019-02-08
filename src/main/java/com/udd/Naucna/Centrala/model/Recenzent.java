package com.udd.Naucna.Centrala.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Recenzent extends Korisnik{
	
	@OneToMany
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
			String grad, String drzava, List<Kupljeno> kupljeno, List<PretplataNaCasopis> pretplate, ArrayList<Casopis> angazovanje,
			String titula, ArrayList<NaucnaOblast> pokrivaNaucneOblasti) {
		super(id, korisnickoIme, lozinka, ime, prezime, email, grad, drzava, kupljeno, pretplate);
		this.angazovanje = angazovanje;
		this.titula = titula;
		this.pokrivaNaucneOblasti = pokrivaNaucneOblasti;
	}

	public Recenzent() {
		super();
	}

	
	
	
}