package com.udd.Naucna.Centrala.dto;

public class RedirekcijaDTO {
	private String taskId;
	private String redirekcija;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getRedirekcija() {
		return redirekcija;
	}
	public void setRedirekcija(String redirekcija) {
		this.redirekcija = redirekcija;
	}
	public RedirekcijaDTO(String taskId, String redirekcija) {
		super();
		this.taskId = taskId;
		this.redirekcija = redirekcija;
	}
	public RedirekcijaDTO() {
		super();
	}
	
}
