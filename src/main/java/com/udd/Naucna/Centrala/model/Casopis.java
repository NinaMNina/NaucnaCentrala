package com.udd.Naucna.Centrala.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
public class Casopis {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String naziv;
	
	@Column
	private boolean openAccess;
	
	@Column(nullable = false)
	@Size(min=0, max=99999999)
	private int ISSN;
	
	@OneToMany
	private List<NaucnaOblast> naucneOblasti;
	
	@OneToOne
	private Urednik urednik;
	
	@OneToMany
	private List<UrednikNO> uredniciNO;
	
	@OneToMany
	private List<Recenzent> recenzenti;
	
	@OneToMany
	private List<Izdanje> izdanja;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

	public int getISSN() {
		return ISSN;
	}

	public void setISSN(int iSSN) {
		ISSN = iSSN;
	}

	public List<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(ArrayList<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public Urednik getUrednik() {
		return urednik;
	}

	public void setUrednik(Urednik urednik) {
		this.urednik = urednik;
	}

	public List<UrednikNO> getUredniciNO() {
		return uredniciNO;
	}

	public void setUredniciNO(ArrayList<UrednikNO> uredniciNO) {
		this.uredniciNO = uredniciNO;
	}

	public List<Recenzent> getRecenzenti() {
		return recenzenti;
	}

	public void setRecenzenti(ArrayList<Recenzent> recenzenti) {
		this.recenzenti = recenzenti;
	}

	public List<Izdanje> getIzdanja() {
		return izdanja;
	}

	public void setIzdanja(List<Izdanje> izdanja) {
		this.izdanja = izdanja;
	}

	public Casopis(Long id, String naziv, boolean openAccess, @Size(min = 0, max = 99999999) int iSSN,
			ArrayList<NaucnaOblast> naucneOblasti, Urednik urednik, ArrayList<UrednikNO> uredniciNO,
			ArrayList<Recenzent> recenzenti, ArrayList<Izdanje> izdanja) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.openAccess = openAccess;
		ISSN = iSSN;
		this.naucneOblasti = naucneOblasti;
		this.urednik = urednik;
		this.uredniciNO = uredniciNO;
		this.recenzenti = recenzenti;
		this.izdanja = izdanja;
	}

	public Casopis() {
		super();
	}
	
	
	
}
