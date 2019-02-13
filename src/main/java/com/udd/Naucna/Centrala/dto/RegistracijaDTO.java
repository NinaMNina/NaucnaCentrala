package com.udd.Naucna.Centrala.dto;

import java.util.ArrayList;

public class RegistracijaDTO {
	private KorisnikDTO korisnikDTO;
	private FormFieldsCamundaDTO formFieldsCamunda;
	
	public KorisnikDTO getKorisnikDTO() {
		return korisnikDTO;
	}
	public void setKorisnikDTO(KorisnikDTO korisnikDTO) {
		this.korisnikDTO = korisnikDTO;
	}
	public FormFieldsCamundaDTO getFormFieldsCamunda() {
		return formFieldsCamunda;
	}
	public void setFormFieldsCamunda(FormFieldsCamundaDTO formFieldsCamunda) {
		this.formFieldsCamunda = formFieldsCamunda;
	}
	public RegistracijaDTO(KorisnikDTO korisnikDTO, FormFieldsCamundaDTO formFieldsCamunda) {
		super();
		this.korisnikDTO = korisnikDTO;
		this.formFieldsCamunda = formFieldsCamunda;
	}
	public RegistracijaDTO() {
		super();
	}
	
	

}
