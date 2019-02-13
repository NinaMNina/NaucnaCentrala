package com.udd.Naucna.Centrala.dto;

import java.util.ArrayList;

import org.springframework.data.geo.Point;

public class FormFieldsCamundaDTO {
	private String taskId;
	private ArrayList<FormFieldDTO> formFields;
	private String processInstanceId;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public ArrayList<FormFieldDTO> getFormFields() {
		return formFields;
	}
	public void setFormFields(ArrayList<FormFieldDTO> formFields) {
		this.formFields = formFields;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public FormFieldsCamundaDTO(String taskId, ArrayList<FormFieldDTO> formFields, String processInstanceId) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
	}
	public FormFieldsCamundaDTO() {
		super();
	}
	public String findImeValue() {
		for(FormFieldDTO f : formFields){
			if(f.getKey().equals("ar_ime"))
				return f.getValue();
		}
		return "";
	}
	public String findPrezimeValue() {
		for(FormFieldDTO f : formFields){
			if(f.getKey().equals("ar_prezime"))
				return f.getValue();
		}
		return "";
	}
	public String findEmailValue() {
		for(FormFieldDTO f : formFields){
			if(f.getKey().equals("ar_mail"))
				return f.getValue();
		}
		return "";
	}
	public Point findPointValue() {
		double lat = 0;
		double lon = 0;
		for(FormFieldDTO f : formFields){
			if(f.getKey().equals("ar_geografskaSirina"))
				lat = new Double(f.getValue());
			if(f.getKey().equals("ar_geografskaDuzina"))
				lon = new Double(f.getValue());
		}
		return new Point(lat, lon);
	}
	
	
}
