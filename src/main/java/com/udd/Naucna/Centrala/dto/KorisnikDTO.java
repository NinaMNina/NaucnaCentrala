package com.udd.Naucna.Centrala.dto;

public class KorisnikDTO {
	private String ime;
	private String lozinka;
	
	public KorisnikDTO(String ime, String lozinka) {
		super();
		this.ime = ime;
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	
}
