package com.udd.Naucna.Centrala.dto;

public class Parametar {
	private String polje;
	private String vrednost;
	private String operacija;
	private Boolean fraza;
	public String getPolje() {
		return polje;
	}
	public void setPolje(String polje) {
		this.polje = polje;
	}
	public String getVrednost() {
		return vrednost;
	}
	public void setVrednost(String vrednost) {
		this.vrednost = vrednost;
	}
	public String getOperacija() {
		return operacija;
	}
	public void setOperacija(String operacija) {
		this.operacija = operacija;
	}
	public Boolean getFraza() {
		return fraza;
	}
	public void setFraza(Boolean fraza) {
		this.fraza = fraza;
	}
	public Parametar(String polje, String vrednost, String operacija, Boolean fraza) {
		super();
		this.polje = polje;
		this.vrednost = vrednost;
		this.operacija = operacija;
		this.fraza = fraza;
	}
	public Parametar() {
		super();
	}
	
	
	
	
	
}
