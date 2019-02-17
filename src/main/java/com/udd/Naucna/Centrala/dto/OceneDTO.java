package com.udd.Naucna.Centrala.dto;

public class OceneDTO {
	private String komentar;
	private String ocena;
	public String getKomentar() {
		return komentar;
	}
	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}
	public String getOcena() {
		return ocena;
	}
	public void setOcena(String ocena) {
		this.ocena = ocena;
	}
	public OceneDTO(String komentar, String ocena) {
		super();
		this.komentar = komentar;
		this.ocena = ocena;
	}
	public OceneDTO() {
		super();
	}
	
	
}
