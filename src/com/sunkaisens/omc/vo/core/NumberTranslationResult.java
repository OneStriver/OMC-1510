package com.sunkaisens.omc.vo.core;

public class NumberTranslationResult {

	private String originalNumber;
	private String mappingNumber;
	private String serviceOption;

	public NumberTranslationResult() {
		super();
	}
	
	public NumberTranslationResult(String originalNumber, String mappingNumber) {
		super();
		this.originalNumber = originalNumber;
		this.mappingNumber = mappingNumber;
	}

	public NumberTranslationResult(String originalNumber, String mappingNumber, String serviceOption) {
		super();
		this.originalNumber = originalNumber;
		this.mappingNumber = mappingNumber;
		this.serviceOption = serviceOption;
	}

	public String getOriginalNumber() {
		return originalNumber;
	}

	public void setOriginalNumber(String originalNumber) {
		this.originalNumber = originalNumber;
	}

	public String getMappingNumber() {
		return mappingNumber;
	}

	public void setMappingNumber(String mappingNumber) {
		this.mappingNumber = mappingNumber;
	}

	public String getServiceOption() {
		return serviceOption;
	}

	public void setServiceOption(String serviceOption) {
		this.serviceOption = serviceOption;
	}

}
