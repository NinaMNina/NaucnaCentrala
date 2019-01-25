package com.udd.Naucna.Centrala.model;

import javax.persistence.*;

@Entity
public class Autor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 40)
	private String ime;
	
	@Column(nullable = false, length = 50)
	private String prezime;

	@Column(nullable = false, length = 120, unique=true)
	private String email;

	@Column(nullable = false, length = 80)
	private String grad;

	@Column(nullable = false, length = 80)
	private String drzava;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public Autor(String ime, String prezime, String email, String grad, String drzava) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.grad = grad;
		this.drzava = drzava;
	}

	public Autor() {
		super();
	}
	
	
	
}
