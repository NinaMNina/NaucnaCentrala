package com.udd.Naucna.Centrala.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.udd.Naucna.Centrala.model.enums.TipKorisnika;

@Entity
public class Korisnik {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false, unique = true)
	private String korisnickoIme;
	
	@Column(nullable = false)
	private String lozinka;
	
	@Enumerated(EnumType.STRING)
	private TipKorisnika tip;

	@OneToMany
	private List<KupljenRad> kupljeniRadovi;
	
	@OneToMany
	private List<PretplataNaCasopis> pretplate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public TipKorisnika getTip() {
		return tip;
	}

	public void setTip(TipKorisnika tip) {
		this.tip = tip;
	}

	public Korisnik(long id, String korisnickoIme, String lozinka, TipKorisnika tip) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.tip = tip;
	}

	public Korisnik() {
		super();
	}
	
	
}
