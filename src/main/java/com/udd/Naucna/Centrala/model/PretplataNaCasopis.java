package com.udd.Naucna.Centrala.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PretplataNaCasopis {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private Casopis casopis;
	
	@Column(nullable = false)
	private Long identifikacioniKod;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datumUplate;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datumOd;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datumDo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}

	public Long getIdentifikacioniKod() {
		return identifikacioniKod;
	}

	public void setIdentifikacioniKod(Long identifikacioniKod) {
		this.identifikacioniKod = identifikacioniKod;
	}

	public Date getDatumUplate() {
		return datumUplate;
	}

	public void setDatumUplate(Date datumUplate) {
		this.datumUplate = datumUplate;
	}

	public Date getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}

	public Date getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}

	public PretplataNaCasopis(long id, Casopis casopis, Long identifikacioniKod, Date datumUplate, Date datumOd,
			Date datumDo) {
		super();
		this.id = id;
		this.casopis = casopis;
		this.identifikacioniKod = identifikacioniKod;
		this.datumUplate = datumUplate;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
	}

	public PretplataNaCasopis() {
		super();
	}
	
	
}
