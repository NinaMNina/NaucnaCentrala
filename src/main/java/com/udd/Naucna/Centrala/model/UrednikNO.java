package com.udd.Naucna.Centrala.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UrednikNO extends Korisnik{
	@ManyToOne
	private Casopis casopis;
	
	@ManyToOne
	private NaucnaOblast naucnaOblast;
	
	@Column(nullable = true, length = 80)
	private String titula;

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}

	public NaucnaOblast getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public String getTitula() {
		return titula;
	}

	public void setTitula(String titula) {
		this.titula = titula;
	}

	
	public UrednikNO(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			String grad, String drzava, List<Kupljeno> kupljeno, List<PretplataNaCasopis> pretplate, Casopis casopis,
			NaucnaOblast naucnaOblast, String titula) {
		super(id, korisnickoIme, lozinka, ime, prezime, email, grad, drzava, kupljeno, pretplate);
		this.casopis = casopis;
		this.naucnaOblast = naucnaOblast;
		this.titula = titula;
	}

	public UrednikNO() {
		super();
	}
	
	
}
