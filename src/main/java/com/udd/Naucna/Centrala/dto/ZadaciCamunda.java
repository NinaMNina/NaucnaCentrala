package com.udd.Naucna.Centrala.dto;

public class ZadaciCamunda {
	private String naziv;
	private String taskId;
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public ZadaciCamunda(String naziv, String taskId) {
		super();
		this.naziv = naziv;
		this.taskId = taskId;
	}
	public ZadaciCamunda() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
