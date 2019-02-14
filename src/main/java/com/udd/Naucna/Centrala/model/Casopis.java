package com.udd.Naucna.Centrala.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.udd.Naucna.Centrala.dto.CasopisDTO;

@Entity
public class Casopis {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String naziv;
	
	@Column
	private Boolean openAccess;
	
	@Column(nullable = false, length=8)
	private String ISSN;
	
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

	public Boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(Boolean openAccess) {
		this.openAccess = openAccess;
	}

	public String getISSN() {
		return ISSN;
	}

	public void setISSN(String iSSN) {
		ISSN = iSSN;
	}

	public List<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(List<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public Urednik getUrednik() {
		return urednik;
	}

	public void setUrednik(Urednik urednik) {
		this.urednik = urednik;
	}

	public Collection<UrednikNO> getUredniciNO() {
		return uredniciNO;
	}

	public void setUredniciNO(List<UrednikNO> uredniciNO) {
		this.uredniciNO = uredniciNO;
	}

	public Collection<Recenzent> getRecenzenti() {
		return recenzenti;
	}

	public void setRecenzenti(List<Recenzent> recenzenti) {
		this.recenzenti = recenzenti;
	}

	public List<Izdanje> getIzdanja() {
		return izdanja;
	}

	public void setIzdanja(List<Izdanje> izdanja) {
		this.izdanja = izdanja;
	}

	public Casopis(Long id, String naziv, Boolean openAccess, String iSSN,
			List<NaucnaOblast> naucneOblasti, Urednik urednik, List<UrednikNO> uredniciNO,
			List<Recenzent> recenzenti, List<Izdanje> izdanja) {
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
	
	public CasopisDTO convertToDTO(){
		CasopisDTO retVal = new CasopisDTO(this.id, this.naziv, this.openAccess, getStringNO(this.getNaucneOblasti()), this.urednik.getKorisnickoIme());
		return retVal;
	}

	private String getStringNO(List<NaucnaOblast> naucneOblasti2) {
		String retVal = "";
		for(NaucnaOblast no : naucneOblasti2){
			retVal+=no.getNazivOblasti();
			retVal+=" - ";
			retVal+=no.getNazivPodOblasti();
			retVal+="<br/>";
		}
		return retVal;
	}
	
	
	
}
