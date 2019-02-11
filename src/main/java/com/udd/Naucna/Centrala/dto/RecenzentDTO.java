package com.udd.Naucna.Centrala.dto;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "recenzenti", type = "recenzenti", shards = 1, replicas = 0)
public class RecenzentDTO {
	@Id
	private Long id;
	@Field(type = FieldType.Text, store = true, index=false)
	private String ime;
	@Field(type = FieldType.Text, store = true, index=false)
	private String prezime;
	@Field(type = FieldType.Text, store = true, index=false)
	private String email;
	@Field(type = FieldType.Text, store = true, index=true)
	private String location;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public RecenzentDTO(Long id, String ime, String prezime, String email, String location) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.location = location;
	}
	public RecenzentDTO() {
		super();
	}
	
	
	
	

}
