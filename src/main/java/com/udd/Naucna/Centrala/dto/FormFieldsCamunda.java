package com.udd.Naucna.Centrala.dto;

import java.util.List;

import org.camunda.bpm.engine.form.FormField;

public class FormFieldsCamunda {
	
	String taskId;
	List<FormField> formFields;
	String processInstanceId;
	
	public FormFieldsCamunda(String taskId, List<FormField> formFields, String processInstanceId) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
	}
	
	public FormFieldsCamunda() {
		super();
	}
	
	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public List<FormField> getFormFields() {
		return formFields;
	}
	
	public void setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	
}
