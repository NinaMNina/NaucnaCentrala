package com.udd.Naucna.Centrala.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.udd.Naucna.Centrala.model.enums.TipKorisnika;

@Entity
public class Urednik {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
	private TipKorisnika tipKorisnika;
	
	@OneToOne
	private Casopis casopis;
	
	@Column(nullable = false, length = 80)
	private String grad;
	
	@Column(nullable = false, length = 80)
	private String drzava;
	
	@Column(nullable = false, length = 80)
	private String titula;
	

	public Long getId() {
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

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
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

	public Urednik(Long id, TipKorisnika tipKorisnika, Casopis casopis, String grad, String drzava, String titula) {
		super();
		this.id = id;
		this.tipKorisnika = tipKorisnika;
		this.casopis = casopis;
		this.grad = grad;
		this.drzava = drzava;
		this.titula = titula;
	}

	public Urednik() {
		super();
	}

	
	
}
