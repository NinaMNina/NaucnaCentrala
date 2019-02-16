package com.udd.Naucna.Centrala.dto;

public class PregledRadDTO {
	private String naslov;
	private String apstrakt;
	private String kljucneReci;
	public String getNaslov() {
		return naslov;
	}
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	public String getApstrakt() {
		return apstrakt;
	}
	public void setApstrakt(String apstrakt) {
		this.apstrakt = apstrakt;
	}
	public String getKljucneReci() {
		return kljucneReci;
	}
	public void setKljucneReci(String kljucneReci) {
		this.kljucneReci = kljucneReci;
	}
	public PregledRadDTO(String naslov, String apstrakt, String kljucneReci) {
		super();
		this.naslov = naslov;
		this.apstrakt = apstrakt;
		this.kljucneReci = kljucneReci;
	}
	public PregledRadDTO() {
		super();
	}
	
	
}
