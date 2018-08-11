package hu.example.redis;

import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class Document {

	private String documentId;
	private Long version;
	private String title;
	private Date creationDate;
	private Date lastModificationDate;
	private Map<String, String> properties;

}
