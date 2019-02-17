package com.udd.Naucna.Centrala.dto;

import java.util.ArrayList;

public class IzmeneDTO {
	private ArrayList<OceneDTO> ocene;
	private ArrayList<FormFieldDTO> podaci;
	
	public ArrayList<OceneDTO> getOcene() {
		return ocene;
	}

	public void setOcene(ArrayList<OceneDTO> ocene) {
		this.ocene = ocene;
	}

	public ArrayList<FormFieldDTO> getPodaci() {
		return podaci;
	}

	public void setPodaci(ArrayList<FormFieldDTO> podaci) {
		this.podaci = podaci;
	}

	public IzmeneDTO(ArrayList<OceneDTO> ocene, ArrayList<FormFieldDTO> podaci) {
		super();
		this.ocene = ocene;
		this.podaci = podaci;
	}

	public IzmeneDTO() {
		super();
	}
	
	
}
