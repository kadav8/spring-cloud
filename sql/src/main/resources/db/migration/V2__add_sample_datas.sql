INSERT
	INTO
		document(
			document_id,
			creation_date,
			last_modification_date,
			title,
			version
		)
	VALUES(
		'S001',
		GETDATE(),
		GETDATE(),
		'My sql document',
		'0'
	);
