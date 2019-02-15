package com.udd.Naucna.Centrala.dto;

import java.util.ArrayList;

import org.camunda.bpm.engine.form.FormField;

public class FormFieldsCamunda {
	private String taskId;
	private ArrayList<FormField> formFields;
	private String processInstanceId;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public ArrayList<FormField> getFormFields() {
		return formFields;
	}
	public void setFormFields(ArrayList<FormField> formFields) {
		this.formFields = formFields;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public FormFieldsCamunda() {
		super();
	}
	public FormFieldsCamunda(String taskId, ArrayList<FormField> formFields, String processInstanceId) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
	}
	
	
	
}
