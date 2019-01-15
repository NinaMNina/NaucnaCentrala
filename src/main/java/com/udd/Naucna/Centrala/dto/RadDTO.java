package com.udd.Naucna.Centrala.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.model.NaucnaOblast;
import com.udd.Naucna.Centrala.model.Recenzent;
import com.udd.Naucna.Centrala.model.enums.StatusRada;

public class RadDTO {
	private Long id;
	private String naslov;
	private String autoriRada;
	private String kljucniPojmovi;
	private String tekstRada;
	private String naucnaOblast;
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
