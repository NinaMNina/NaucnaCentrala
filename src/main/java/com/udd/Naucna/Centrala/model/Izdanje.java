package com.udd.Naucna.Centrala.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Izdanje {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datumVazenjaOd;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datumVazenjaDo;
	
	@OneToMany
	private List<Rad> radovi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatumVazenjaOd() {
		return datumVazenjaOd;
	}

	public void setDatumVazenjaOd(Date datumVazenjaOd) {
		this.datumVazenjaOd = datumVazenjaOd;
	}

	public Date getDatumVazenjaDo() {
		return datumVazenjaDo;
	}

	public void setDatumVazenjaDo(Date datumVazenjaDo) {
		this.datumVazenjaDo = datumVazenjaDo;
	}

	public List<Rad> getRadovi() {
		return radovi;
	}

	public void setRadovi(List<Rad> radovi) {
		this.radovi = radovi;
	}

	public Izdanje(Long id, Date datumVazenjaOd, Date datumVazenjaDo, List<Rad> radovi) {
		super();
		this.id = id;
		this.datumVazenjaOd = datumVazenjaOd;
		this.datumVazenjaDo = datumVazenjaDo;
		this.radovi = radovi;
	}

	public Izdanje() {
		super();
	}
	
	
}
