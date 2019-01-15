package com.udd.Naucna.Centrala.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.udd.Naucna.Centrala.model.enums.TipKorisnika;

@Entity
public class Recenzent extends Korisnik{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
	private TipKorisnika tipKorisnika;
	
	@OneToMany
	private List<Casopis> angazovanje;
	
	@Column(nullable = false, length = 80)
	private String grad;
	
	@Column(nullable = false, length = 80)
	private String drzava;
	
	@Column(nullable = false, length = 80)
	private String titula;
	
	@OneToMany
	private List<NaucnaOblast> pokrivaNaucneOblasti;

	@OneToOne(optional=true)
	@PrimaryKeyJoinColumn
	private Korisnik korisnik;
	
	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}

	public List<Casopis> getAngazovanje() {
		return angazovanje;
	}

	public void setAngazovanje(ArrayList<Casopis> angazovanje) {
		this.angazovanje = angazovanje;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
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

	public Recenzent(Long id, TipKorisnika tipKorisnika, ArrayList<Casopis> angazovanje, String grad, String drzava,
			String titula, ArrayList<NaucnaOblast> pokrivaNaucneOblasti, Korisnik korisnik) {
		super();
		this.id = id;
		this.tipKorisnika = tipKorisnika;
		this.angazovanje = angazovanje;
		this.grad = grad;
		this.drzava = drzava;
		this.titula = titula;
		this.pokrivaNaucneOblasti = pokrivaNaucneOblasti;
		this.korisnik = korisnik;
	}

	public Recenzent() {
		super();
	}

	
	
	
}