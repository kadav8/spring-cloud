package com.example.mongo;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.mongo.domain.Document;
import com.example.mongo.domain.DocumentType;
import com.example.mongo.repository.DocumentRepository;
import com.example.mongo.repository.DocumentTypeRepository;
import com.example.mongo.service.DocumentSavingService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoApplicationTests {

	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private DocumentTypeRepository documentTypeRepository;
	@Autowired
	private DocumentSavingService documentSavingService;

	@Test
	public void saveDocuments() {
		if(!documentTypeRepository.existsById("SimpleDocument").block()) {
			DocumentType documentType = new DocumentType();
			documentType.setTypeName("SimpleDocument");
			Map<String, String> pd = new HashMap<>();
			pd.put("ClientNumber", "String");
			documentType.setPropertyDefinitions(pd);
			documentTypeRepository.save(documentType).block();
		}

		String id = UUID.randomUUID().toString();
		String vid = UUID.randomUUID().toString();
		Document document = new Document();
		document.setDocumentId(id);
		document.setVersionSeriesId(vid);
		document.setDataType("SimpleDocument");
		Map<String, String> sp = new HashMap<>();
		sp.put("ClientNumber", "alma12");
		document.setStringProperties(sp);
		documentSavingService.saveDocument(document).block();

		assertNotNull(documentRepository.findById(id).block());
	}
}
