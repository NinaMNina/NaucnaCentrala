package com.udd.Naucna.Centrala.dto;

//@Document(indexName = "recenzenti", type = "recenzenti", shards = 1, replicas = 0)
public class RecenzentDTO {
//	@Id
	private Long id;
//	@Field(type = FieldType.Text, store = true, index=false)
	private String ime;
//	@Field(type = FieldType.Text, store = true, index=false)
	private String prezime;
//	@Field(type = FieldType.Text, store = true, index=false)
	private String email;
//	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String casopis;
//	@GeoPointField
//	private GeoPoint location;
//	@Field(type = FieldType.Text, store = true, index=true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String tekstovi;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
/*	public GeoPoint getLocation() {
		return location;
	}
	public void setLocation(GeoPoint location) {
		this.location = location;
	}*/
	public String getCasopis() {
		return casopis;
	}
	public void setCasopis(String casopis) {
		this.casopis = casopis;
	}
	public String getTekstovi() {
		return tekstovi;
	}
	public void setTekstovi(String tekstovi) {
		this.tekstovi = tekstovi;
	}
	public RecenzentDTO(Long id, String ime, String prezime, String email, String casopis, 
			String tekstovi) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.casopis = casopis;
		this.tekstovi = tekstovi;
	}
	public RecenzentDTO() {
		super();
	}
	
	

}
