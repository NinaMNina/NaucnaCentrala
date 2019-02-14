package com.udd.Naucna.Centrala.dto;

public class CasopisDTO {
	private Long id;
	private String naziv;
	private boolean openAccess;
	private String naucneOblasti;
	private String urednikKorisnickoIme;
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
	public String getNaucneOblasti() {
		return naucneOblasti;
	}
	public void setNaucneOblasti(String naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}
	public String getUrednikKorisnickoIme() {
		return urednikKorisnickoIme;
	}
	public void setUrednikKorisnickoIme(String urednikKorisnickoIme) {
		this.urednikKorisnickoIme = urednikKorisnickoIme;
	}
	public CasopisDTO(Long id, String naziv, boolean openAccess, String naucneOblasti, String urednikKorisnickoIme) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.openAccess = openAccess;
		this.naucneOblasti = naucneOblasti;
		this.urednikKorisnickoIme = urednikKorisnickoIme;
	}
	public CasopisDTO() {
		super();
	}

}
