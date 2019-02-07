package com.udd.Naucna.Centrala.dto;

public class HighlightedRadDTO {
	private RadDTO highlights;
	private RadDTO rad;
	public RadDTO getHighlights() {
		return highlights;
	}
	public void setHighlights(RadDTO highlights) {
		this.highlights = highlights;
	}
	public RadDTO getRad() {
		return rad;
	}
	public void setRad(RadDTO rad) {
		this.rad = rad;
	}
	public HighlightedRadDTO(RadDTO highlights, RadDTO rad) {
		super();
		this.highlights = highlights;
		this.rad = rad;
	}
	public HighlightedRadDTO( ) {
		super();
	}
	

}
