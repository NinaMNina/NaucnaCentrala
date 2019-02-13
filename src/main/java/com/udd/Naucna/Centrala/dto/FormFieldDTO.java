package com.udd.Naucna.Centrala.dto;

import com.udd.Naucna.Centrala.model.enums.TipPolja;

public class FormFieldDTO {
	private String key;
	private String value;
	private TipPolja tip;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public TipPolja getTip() {
		return tip;
	}
	public void setTip(TipPolja tip) {
		this.tip = tip;
	}
	public FormFieldDTO(String key, String value, TipPolja tip) {
		super();
		this.key = key;
		this.value = value;
		this.tip = tip;
	}
	public FormFieldDTO() {
		super();
	}
	
	
}
