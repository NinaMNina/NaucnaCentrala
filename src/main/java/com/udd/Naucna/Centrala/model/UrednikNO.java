package com.udd.Naucna.Centrala.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.data.geo.Point;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class UrednikNO extends Recenzent{
	@ManyToOne
	@JsonBackReference
	private Casopis angazovanKaoUradnikZaCasopis;
	
	@ManyToOne
	private NaucnaOblast odgovoranZaNaucnuOblast;

	public Casopis getAngazovanKaoUradnikZaCasopis() {
		return angazovanKaoUradnikZaCasopis;
	}

	public void setAngazovanKaoUradnikZaCasopis(Casopis angazovanKaoUradnikZaCasopis) {
		this.angazovanKaoUradnikZaCasopis = angazovanKaoUradnikZaCasopis;
	}

	public NaucnaOblast getOdgovoranZaNaucnuOblast() {
		return odgovoranZaNaucnuOblast;
	}

	public void setOdgovoranZaNaucnuOblast(NaucnaOblast odgovoranZaNaucnuOblast) {
		this.odgovoranZaNaucnuOblast = odgovoranZaNaucnuOblast;
	}

	public UrednikNO(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			Point lokacija, List<Kupljeno> kupljeno, List<PretplataNaCasopis> pretplate, List<Casopis> angazovanje,
			String titula, List<NaucnaOblast> pokrivaNaucneOblasti, Casopis angazovanKaoUradnikZaCasopis,
			NaucnaOblast odgovoranZaNaucnuOblast) {
		super(id, korisnickoIme, lozinka, ime, prezime, email, lokacija, kupljeno, pretplate, angazovanje, titula,
				pokrivaNaucneOblasti);
		this.angazovanKaoUradnikZaCasopis = angazovanKaoUradnikZaCasopis;
		this.odgovoranZaNaucnuOblast = odgovoranZaNaucnuOblast;
	}

	public UrednikNO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UrednikNO(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			Point lokacija, List<Kupljeno> kupljeno, List<PretplataNaCasopis> pretplate, List<Casopis> angazovanje,
			String titula, List<NaucnaOblast> pokrivaNaucneOblasti) {
		super(id, korisnickoIme, lozinka, ime, prezime, email, lokacija, kupljeno, pretplate, angazovanje, titula,
				pokrivaNaucneOblasti);
		// TODO Auto-generated constructor stub
	}

	public UrednikNO(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			Point lokacija, List<Kupljeno> kupljeno, List<PretplataNaCasopis> pretplate) {
		super(id, korisnickoIme, lozinka, ime, prezime, email, lokacija, kupljeno, pretplate);
		// TODO Auto-generated constructor stub
	}






	
	
	
	
}
