package com.udd.Naucna.Centrala.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Proces {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, length = 120)
	private String autor;
	@Column(nullable = true, length = 120)
	private String urednik;
	@Column(nullable = true, length = 120)
	private String urednikNO;
	@Column(nullable = true)
	private ArrayList<String> recenzenti;
	@Column(nullable = true)
	private Long casopisId;
	@Column(nullable = false, length = 120, unique=true)
	private String processInstanceId;
	@Column(nullable = true, length = 120)
	private String taskId;
	@Column(nullable = true)
	private Long naucnaOblastId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getUrednik() {
		return urednik;
	}
	public void setUrednik(String urednik) {
		this.urednik = urednik;
	}
	public String getUrednikNO() {
		return urednikNO;
	}
	public void setUrednikNO(String urednikNO) {
		this.urednikNO = urednikNO;
	}
	
	public Long getCasopisId() {
		return casopisId;
	}
	public void setCasopisId(Long casopisId) {
		this.casopisId = casopisId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Long getNaucnaOblastId() {
		return naucnaOblastId;
	}
	public void setNaucnaOblastId(Long naucnaOblastId) {
		this.naucnaOblastId = naucnaOblastId;
	}
	
	public ArrayList<String> getRecenzenti() {
		return recenzenti;
	}
	public void setRecenzenti(ArrayList<String> recenzenti) {
		this.recenzenti = recenzenti;
	}
	public Proces(Long id, String autor, String urednik, String urednikNO, ArrayList<String> recenzenti, Long casopisId,
			String processInstanceId, String taskId, Long naucnaOblastId) {
		super();
		this.id = id;
		this.autor = autor;
		this.urednik = urednik;
		this.urednikNO = urednikNO;
		this.recenzenti = recenzenti;
		this.casopisId = casopisId;
		this.processInstanceId = processInstanceId;
		this.taskId = taskId;
		this.naucnaOblastId = naucnaOblastId;
	}
	public Proces() {
		super();
	}
	
	
	
	
	
}
