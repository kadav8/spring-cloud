package com.example.mongo.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;

public class DocumentType {

	@Id
	private String typeName;
	private Map<String, String> propertyDefinitions;

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Map<String, String> getPropertyDefinitions() {
		return propertyDefinitions;
	}
	public void setPropertyDefinitions(Map<String, String> propertyDefinitions) {
		this.propertyDefinitions = propertyDefinitions;
	}

	@Override
	public String toString() {
		return "DocumentType [typeName=" + typeName + "]";
	}
}
