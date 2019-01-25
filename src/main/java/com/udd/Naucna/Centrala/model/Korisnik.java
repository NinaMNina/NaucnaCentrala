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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

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
	
	@OneToOne(optional=true)
	@PrimaryKeyJoinColumn
	private Autor autor;
	
	@OneToOne(optional=true)
	@PrimaryKeyJoinColumn
	private Recenzent recenzent;
	
	@OneToOne(optional=true)
	@PrimaryKeyJoinColumn
	private Urednik udernik;

	@OneToOne(optional=true)
	@PrimaryKeyJoinColumn
	private UrednikNO urednikNO;
	
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

	public List<KupljenRad> getKupljeniRadovi() {
		return kupljeniRadovi;
	}

	public void setKupljeniRadovi(List<KupljenRad> kupljeniRadovi) {
		this.kupljeniRadovi = kupljeniRadovi;
	}

	public List<PretplataNaCasopis> getPretplate() {
		return pretplate;
	}

	public void setPretplate(List<PretplataNaCasopis> pretplate) {
		this.pretplate = pretplate;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Recenzent getRecenzent() {
		return recenzent;
	}

	public void setRecenzent(Recenzent recenzent) {
		this.recenzent = recenzent;
	}

	public Urednik getUdernik() {
		return udernik;
	}

	public void setUdernik(Urednik udernik) {
		this.udernik = udernik;
	}

	public UrednikNO getUrednikNO() {
		return urednikNO;
	}

	public void setUrednikNO(UrednikNO urednikNO) {
		this.urednikNO = urednikNO;
	}
	
	public Korisnik(String korisnickoIme, String lozinka, TipKorisnika tip, List<KupljenRad> kupljeniRadovi,
			List<PretplataNaCasopis> pretplate, Autor autor, Recenzent recenzent, Urednik udernik,
			UrednikNO urednikNO) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.tip = tip;
		this.kupljeniRadovi = kupljeniRadovi;
		this.pretplate = pretplate;
		this.autor = autor;
		this.recenzent = recenzent;
		this.udernik = udernik;
		this.urednikNO = urednikNO;
	}

	public Korisnik() {
		super();
	}
	
	
}
