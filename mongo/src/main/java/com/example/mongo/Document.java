package com.example.mongo;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import lombok.Data;

@Data
public class Document {

	@Id
	private String documentId;

	@Version
	private Long version;

	private String title;
	private Date creationDate;
	private Date lastModificationDate;
	private Map<String, String> properties;

}
