package com.udd.Naucna.Centrala.dto;

import java.util.ArrayList;

public class ParametriDTO {
	private ArrayList<Parametar> parametri;

	public ArrayList<Parametar> getParametri() {
		return parametri;
	}

	public void setParametri(ArrayList<Parametar> parametri) {
		this.parametri = parametri;
	}

	public ParametriDTO(ArrayList<Parametar> parametri) {
		super();
		this.parametri = parametri;
	}

	public ParametriDTO() {
		super();
	}

	public String getByKey(String string) {
		for(Parametar p : this.getParametri()){
			if(p.getPolje().equals(string))
				return p.getVrednost();
		}
		return "";
	}
	
	

}
