package com.example.mongo.domain;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public class Document {

	@Id
	private String documentId;
	private String versionSeriesId;
	@Version
	private Long version;
	private String type;
	private Date creationDate;
	private String creatorName;
	private Date lastModificationDate;
	private String modifierName;

	private Map<String, String> stringProperties;
	private Map<String, Long> longProperties;
	private Map<String, Double> doubleProperties;
	private Map<String, Date> dateProperties;

	public Document() {}

	public Document(Document document) {
		this.documentId = document.getDocumentId();
		this.versionSeriesId = document.getVersionSeriesId();
		this.version = document.getVersion();
		this.type = document.getType();
		this.creationDate = document.getCreationDate();
		this.creatorName = document.getCreatorName();
		this.lastModificationDate = document.getLastModificationDate();
		this.modifierName = document.getModifierName();
		this.stringProperties = document.getStringProperties();
		this.longProperties = document.getLongProperties();
		this.doubleProperties = document.getDoubleProperties();
		this.dateProperties = document.getDateProperties();
	}

	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getVersionSeriesId() {
		return versionSeriesId;
	}
	public void setVersionSeriesId(String versionSeriesId) {
		this.versionSeriesId = versionSeriesId;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Date getLastModificationDate() {
		return lastModificationDate;
	}
	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
	public String getModifierName() {
		return modifierName;
	}
	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}
	public Map<String, String> getStringProperties() {
		return stringProperties;
	}
	public void setStringProperties(Map<String, String> stringProperties) {
		this.stringProperties = stringProperties;
	}
	public Map<String, Long> getLongProperties() {
		return longProperties;
	}
	public void setLongProperties(Map<String, Long> longProperties) {
		this.longProperties = longProperties;
	}
	public Map<String, Double> getDoubleProperties() {
		return doubleProperties;
	}
	public void setDoubleProperties(Map<String, Double> doubleProperties) {
		this.doubleProperties = doubleProperties;
	}
	public Map<String, Date> getDateProperties() {
		return dateProperties;
	}
	public void setDateProperties(Map<String, Date> dateProperties) {
		this.dateProperties = dateProperties;
	}

	@Override
	public String toString() {
		return "Document [documentId=" + documentId + ", versionSeriesId=" + versionSeriesId + ", type=" + type + "]";
	}
}
