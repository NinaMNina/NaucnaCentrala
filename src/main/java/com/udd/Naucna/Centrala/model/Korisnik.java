package com.udd.Naucna.Centrala.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.geo.Point;

@Entity
public class Korisnik {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String korisnickoIme;
	
	@Column(nullable = false)
	private String lozinka;
	
	@Column(nullable = false, length = 40)
	private String ime;
	
	@Column(nullable = false, length = 50)
	private String prezime;

	@Column(nullable = false, length = 120, unique=true)
	private String email;

	@Column(nullable = true)
	private Point lokacija;


	@OneToMany
	private List<Kupljeno> kupljeno;
	
	@OneToMany
	private List<PretplataNaCasopis> pretplate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Point getLokacija() {
		return lokacija;
	}

	public void setLokacija(Point lokacija) {
		this.lokacija = lokacija;
	}

	public List<Kupljeno> getKupljeno() {
		return kupljeno;
	}

	public void setKupljeno(List<Kupljeno> kupljeno) {
		this.kupljeno = kupljeno;
	}

	public List<PretplataNaCasopis> getPretplate() {
		return pretplate;
	}

	public void setPretplate(List<PretplataNaCasopis> pretplate) {
		this.pretplate = pretplate;
	}

	public Korisnik(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			Point lokacija, List<Kupljeno> kupljeno, List<PretplataNaCasopis> pretplate) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lokacija = lokacija;
		this.kupljeno = kupljeno;
		this.pretplate = pretplate;
	}

	public Korisnik() {
		super();
	}

}
