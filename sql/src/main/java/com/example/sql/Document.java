package com.example.sql;

import java.util.Date;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Document {

	@Id
	private String documentId;

	private Long version;
	private String title;
	private Date creationDate;
	private Date lastModificationDate;

	@ElementCollection
	private Map<String, String> properties;

}
