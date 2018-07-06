package com.sunkaisens.omc.vo.core;

public class ComboboxVo {
	private String valueField;
	private String textField;

	public ComboboxVo() {

	}

	public ComboboxVo(String valueField, String textField) {
		this.valueField = valueField;
		this.textField = textField;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

}