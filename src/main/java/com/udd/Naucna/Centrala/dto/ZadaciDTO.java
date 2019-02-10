package com.udd.Naucna.Centrala.dto;

import com.udd.Naucna.Centrala.model.enums.ZadatakTip;

public class ZadaciDTO {
	private String opis;
	private Long rad;
	private ZadatakTip tip;

	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public Long getRad() {
		return rad;
	}
	public void setRad(Long rad) {
		this.rad = rad;
	}
	public ZadatakTip getTip() {
		return tip;
	}
	public void setTip(ZadatakTip tip) {
		this.tip = tip;
	}
	public ZadaciDTO( String opis, Long rad, ZadatakTip tip) {
		super();
		this.opis = opis;
		this.rad = rad;
		this.tip = tip;
	}
	public ZadaciDTO() {
		super();
	}
	
	
	
}
