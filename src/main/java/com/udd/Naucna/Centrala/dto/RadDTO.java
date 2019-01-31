package com.udd.Naucna.Centrala.dto;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.udd.Naucna.Centrala.model.enums.StatusRada;

@Document(indexName = "radovi", type = "rad", shards = 1, replicas = 0)
public class RadDTO {
	@Id
	private Long id;
	
	@Field(type = FieldType.text, store = true)
	private String naslov;

	@Field(type = FieldType.text, store = true)
	private String autoriRada;
	
	@Field(type = FieldType.text, store = true)
	private String kljucniPojmovi;

	@Field(type = FieldType.text, store = true)
	private String tekstRada;

	@Field(type = FieldType.text, store = true)
	private String naucnaOblast;

	@Field(type = FieldType.text, store = true)
    private StatusRada status;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public StatusRada getStatus() {
		return status;
	}
	public void setStatus(StatusRada status) {
		this.status = status;
	}
	public RadDTO(Long id, String naslov, String autoriRada, String kljucniPojmovi, String tekstRada,
			String naucnaOblast, StatusRada status) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.autoriRada = autoriRada;
		this.kljucniPojmovi = kljucniPojmovi;
		this.tekstRada = tekstRada;
		this.naucnaOblast = naucnaOblast;
		this.status = status;
	}
	public RadDTO() {
		super();
	}
	
	
}
