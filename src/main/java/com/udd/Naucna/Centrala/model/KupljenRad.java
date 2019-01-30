package com.udd.Naucna.Centrala.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.udd.Naucna.Centrala.model.enums.TransakcijaStatus;

@Entity
public class KupljenRad {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private Rad rad;
	
	@Column(nullable = false)
	private Long identifikacioniKod;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datum;
	
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
	private TransakcijaStatus status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Rad getRad() {
		return rad;
	}

	public void setRad(Rad rad) {
		this.rad = rad;
	}

	public Long getIdentifikacioniKod() {
		return identifikacioniKod;
	}

	public void setIdentifikacioniKod(Long identifikacioniKod) {
		this.identifikacioniKod = identifikacioniKod;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public TransakcijaStatus getStatus() {
		return status;
	}

	public void setStatus(TransakcijaStatus status) {
		this.status = status;
	}

	public KupljenRad(long id, Rad rad, Long identifikacioniKod, Date datum, TransakcijaStatus status) {
		super();
		this.id = id;
		this.rad = rad;
		this.identifikacioniKod = identifikacioniKod;
		this.datum = datum;
		this.status = status;
	}

	public KupljenRad() {
		super();
	}
	
	
}
