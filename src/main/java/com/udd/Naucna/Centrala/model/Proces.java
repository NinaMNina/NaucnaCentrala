package com.udd.Naucna.Centrala.model;

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
	private String urednikNO1;
	@Column(nullable = true, length = 120)
	private String urednikNO2;
	@Column(nullable = true, length = 120)
	private String urednikNO3;
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
	public String getUrednikNO1() {
		return urednikNO1;
	}
	public void setUrednikNO1(String urednikNO1) {
		this.urednikNO1 = urednikNO1;
	}
	public String getUrednikNO2() {
		return urednikNO2;
	}
	public void setUrednikNO2(String urednikNO2) {
		this.urednikNO2 = urednikNO2;
	}
	public String getUrednikNO3() {
		return urednikNO3;
	}
	public void setUrednikNO3(String urednikNO3) {
		this.urednikNO3 = urednikNO3;
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
	public Proces(Long id, String autor, String urednik, String urednikNO1, String urednikNO2,
			String urednikNO3, Long casopisId, String processInstanceId, String taskId, Long naucnaOblastId) {
		super();
		this.id = id;
		this.autor = autor;
		this.urednik = urednik;
		this.urednikNO1 = urednikNO1;
		this.urednikNO2 = urednikNO2;
		this.urednikNO3 = urednikNO3;
		this.casopisId = casopisId;
		this.processInstanceId = processInstanceId;
		this.taskId = taskId;
		this.naucnaOblastId = naucnaOblastId;
	}
	public Proces() {
		super();
	}
	
	
	
	
	
}
