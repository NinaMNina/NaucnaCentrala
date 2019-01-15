package com.udd.Naucna.Centrala.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.udd.Naucna.Centrala.model.enums.StatusRada;

@Entity
public class Rad {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 240)
	private String naslov;
	
	@OneToMany
	private List<Autor> autoriRada;
	
	@Column(nullable = false, length = 1200)
	private String kljucniPojmovi;
	
	@Column(nullable = false, length = 300)
	private String lokacijaProbnogRada;
	
	@Column(nullable = true, length = 300)
	private String lokacijaRada;
	
	@ManyToOne
	private NaucnaOblast naucnaOblast;
	
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusRada status;
	
	@OneToMany
	private List<Recenzent> recenzenti;

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

	public String getLokacijaProbnogRada() {
		return lokacijaProbnogRada;
	}

	public void setLokacijaProbnogRada(String lokacijaProbnogRada) {
		this.lokacijaProbnogRada = lokacijaProbnogRada;
	}

	public String getLokacijaRada() {
		return lokacijaRada;
	}

	public void setLokacijaRada(String lokacijaRada) {
		this.lokacijaRada = lokacijaRada;
	}

	public NaucnaOblast getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public StatusRada getStatus() {
		return status;
	}

	public void setStatus(StatusRada status) {
		this.status = status;
	}

	public List<Recenzent> getRecenzenti() {
		return recenzenti;
	}

	public void setRecenzenti(List<Recenzent> recenzenti) {
		this.recenzenti = recenzenti;
	}

	public Rad(Long id, String naslov, List<Autor> autoriRada, String kljucniPojmovi, String lokacijaProbnogRada,
			String lokacijaRada, NaucnaOblast naucnaOblast, StatusRada status, List<Recenzent> recenzenti) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.autoriRada = autoriRada;
		this.kljucniPojmovi = kljucniPojmovi;
		this.lokacijaProbnogRada = lokacijaProbnogRada;
		this.lokacijaRada = lokacijaRada;
		this.naucnaOblast = naucnaOblast;
		this.status = status;
		this.recenzenti = recenzenti;
	}

	public Rad() {
		super();
	}
	
	
	
	
	
	
	
	
	
}
