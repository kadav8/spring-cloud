CREATE TABLE
		document(
			document_id VARCHAR(255) NOT NULL,
			creation_date TIMESTAMP,
			last_modification_date TIMESTAMP,
			title VARCHAR(255),
			version BIGINT,
			PRIMARY KEY(document_id)
		);

CREATE TABLE
		document_properties(
			document_document_id VARCHAR(255) NOT NULL,
			properties VARCHAR(255),
			properties_key VARCHAR(255) NOT NULL,
			PRIMARY KEY(
				document_document_id,
				properties_key
			)
		);

ALTER TABLE
	document_properties ADD CONSTRAINT FKrdeupcno4h1n59xxs2thn3hq5 FOREIGN KEY(document_document_id) REFERENCES document;
