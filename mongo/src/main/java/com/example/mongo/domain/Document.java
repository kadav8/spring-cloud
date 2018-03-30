package com.example.mongo.domain;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public class Document {

	@Id
	private String documentId;
	private String VersionSeriesId;
	@Version
	private Long version;
	private Boolean isCheckedOut;
	private String dataType;
	private Integer minorVersion;
	private Integer majorVersion;
	private Date creationDate;
	private Date lastModifiedDate;
	private String creatorName;
	private String lastModifierName;

	private Map<String, String> stringProperties;
	private Map<String, Long> longProperties;
	private Map<String, Double> doubleProperties;
	private Map<String, Date> dateProperties;

	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getVersionSeriesId() {
		return VersionSeriesId;
	}
	public void setVersionSeriesId(String versionSeriesId) {
		VersionSeriesId = versionSeriesId;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Boolean getIsCheckedOut() {
		return isCheckedOut;
	}
	public void setIsCheckedOut(Boolean isCheckedOut) {
		this.isCheckedOut = isCheckedOut;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getMinorVersion() {
		return minorVersion;
	}
	public void setMinorVersion(Integer minorVersion) {
		this.minorVersion = minorVersion;
	}
	public Integer getMajorVersion() {
		return majorVersion;
	}
	public void setMajorVersion(Integer majorVersion) {
		this.majorVersion = majorVersion;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getLastModifierName() {
		return lastModifierName;
	}
	public void setLastModifierName(String lastModifierName) {
		this.lastModifierName = lastModifierName;
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
		return "Document [documentId=" + documentId + ", VersionSeriesId=" + VersionSeriesId + ", minorVersion="
				+ minorVersion + ", majorVersion=" + majorVersion + "]";
	}
}
