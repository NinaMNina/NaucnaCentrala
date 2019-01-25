package com.udd.Naucna.Centrala.dto;

public class RegistracijaDTO {
	private KorisnikDTO korisnikDTO;
	private FormFieldsCamunda formFieldsCamunda;
	
	public KorisnikDTO getKorisnikDTO() {
		return korisnikDTO;
	}
	public void setKorisnikDTO(KorisnikDTO korisnikDTO) {
		this.korisnikDTO = korisnikDTO;
	}
	public FormFieldsCamunda getFormFieldsCamunda() {
		return formFieldsCamunda;
	}
	public void setFormFieldsCamunda(FormFieldsCamunda formFieldsCamunda) {
		this.formFieldsCamunda = formFieldsCamunda;
	}
	public RegistracijaDTO(KorisnikDTO korisnikDTO, FormFieldsCamunda formFieldsCamunda) {
		super();
		this.korisnikDTO = korisnikDTO;
		this.formFieldsCamunda = formFieldsCamunda;
	}
	public RegistracijaDTO() {
		super();
	}
	
	

}
