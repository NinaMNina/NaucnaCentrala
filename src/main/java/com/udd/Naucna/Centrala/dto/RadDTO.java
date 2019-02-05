package com.udd.Naucna.Centrala.dto;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.udd.Naucna.Centrala.model.Autor;

@Document(indexName = "radovi", type = "radovi", shards = 1, replicas = 0)
public class RadDTO {
	@Id
	private Long id;
	
	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String casopis;
	
	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String naslov;

	@Field(type = FieldType.Nested, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private List<Autor> autoriRada;
	
	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String kljucniPojmovi;

	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String tekstRada;

	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String naucnaOblast;
	
	@Field(type = FieldType.Text, store = true, index=false, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
    private String cistTekst;
	
	@Field(type = FieldType.Text, store = true, index=false)
    private String file;
	
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


	public List<Autor> getAutoriRada() {
		return autoriRada;
	}


	public void setAutoriRada(List<Autor> autoriRada) {
		this.autoriRada = autoriRada;
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


	public String getCistTekst() {
		return cistTekst;
	}
	

	public String getFile() {
		return file;
	}


	public void setFile(String file) {
		this.file = file;
	}


	public void setCistTekst(String cistTekst) {
		this.cistTekst = cistTekst;
	}
	

	public RadDTO() {
		super();
	}


	public RadDTO(Long id, String casopis, String naslov, List<Autor> autoriRada, String kljucniPojmovi, String tekstRada,
			String naucnaOblast, String cistTekst, String file) {
		super();
		this.id = id;
		this.casopis =casopis;
		this.naslov = naslov;
		this.autoriRada = autoriRada;
		this.kljucniPojmovi = kljucniPojmovi;
		this.tekstRada = tekstRada;
		this.naucnaOblast = naucnaOblast;
		this.cistTekst = cistTekst;
		this.file = file;
	}
	
	
}
