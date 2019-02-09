package com.udd.Naucna.Centrala.dto;

import javax.persistence.Id;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "radovi", type = "radovi", shards = 1, replicas = 0)
public class RadDTO {
	@Id
	private Long id;
	
	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String casopis;
	
	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String naslov;

	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String autoriRada;
	
	@Field(type = FieldType.Nested, store = true, index = false)
	private GeoPoint lokacija;
	
	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String kljucniPojmovi;

	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String tekstRada;

	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String naucnaOblast;
	
	@Field(type = FieldType.Text, store = true, index=false)
    private String file;
	
	@Field(type = FieldType.Boolean, store = true, index=false)
    private Boolean free;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCasopis() {
		return casopis;
	}

	public void setCasopis(String casopis) {
		this.casopis = casopis;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getAutoriRada() {
		return autoriRada;
	}

	public void setAutoriRada(String autoriRada) {
		this.autoriRada = autoriRada;
	}

	public GeoPoint getLokacija() {
		return lokacija;
	}

	public void setLokacija(GeoPoint lokacija) {
		this.lokacija = lokacija;
	}

	public String getKljucniPojmovi() {
		return kljucniPojmovi;
	}

	public void setKljucniPojmovi(String kljucniPojmovi) {
		this.kljucniPojmovi = kljucniPojmovi;
	}

	public String getTekstRada() {
		return tekstRada;
	}

	public void setTekstRada(String tekstRada) {
		this.tekstRada = tekstRada;
	}

	public String getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(String naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Boolean getFree() {
		return free;
	}

	public void setFree(Boolean free) {
		this.free = free;
	}

	public RadDTO(Long id, String casopis, String naslov, String autoriRada, GeoPoint lokacija, String kljucniPojmovi,
			String tekstRada, String naucnaOblast, String file, Boolean free) {
		super();
		this.id = id;
		this.casopis = casopis;
		this.naslov = naslov;
		this.autoriRada = autoriRada;
		this.lokacija = lokacija;
		this.kljucniPojmovi = kljucniPojmovi;
		this.tekstRada = tekstRada;
		this.naucnaOblast = naucnaOblast;
		this.file = file;
		this.free = free;
	}

	public RadDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
}
