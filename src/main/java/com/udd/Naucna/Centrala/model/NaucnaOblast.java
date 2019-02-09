package com.udd.Naucna.Centrala.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class NaucnaOblast {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String nazivOblasti;
	
	@Column(nullable = false)
	private int kodOblasti;
	
	@Column(nullable = false, length = 120)
	private String nazivPodOblasti;
	
	@Column(nullable = false)
	private int kodPodOblasti;
	
	@Column(nullable = true, length = 240)
	private String detalji;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazivOblasti() {
		return nazivOblasti;
	}

	public void setNazivOblasti(String nazivOblasti) {
		this.nazivOblasti = nazivOblasti;
	}

	public int getKodOblasti() {
		return kodOblasti;
	}

	public void setKodOblasti(int kodOblasti) {
		this.kodOblasti = kodOblasti;
	}

	public String getNazivPodOblasti() {
		return nazivPodOblasti;
	}

	public void setNazivPodOblasti(String nazivPodOblasti) {
		this.nazivPodOblasti = nazivPodOblasti;
	}

	public int getKodPodOblasti() {
		return kodPodOblasti;
	}

	public void setKodPodOblasti(int kodPodOblasti) {
		this.kodPodOblasti = kodPodOblasti;
	}

	public String getDetalji() {
		return detalji;
	}

	public void setDetalji(String detalji) {
		this.detalji = detalji;
	}

	public NaucnaOblast(Long id, String nazivOblasti, @Size(min = 1, max = 6) int kodOblasti, String nazivPodOblasti,
			@Size(min = 1, max = 15) int kodPodOblasti, String detalji) {
		super();
		this.id = id;
		this.nazivOblasti = nazivOblasti;
		this.kodOblasti = kodOblasti;
		this.nazivPodOblasti = nazivPodOblasti;
		this.kodPodOblasti = kodPodOblasti;
		this.detalji = detalji;
	}

	public NaucnaOblast() {
		super();
	}
	
	
}
