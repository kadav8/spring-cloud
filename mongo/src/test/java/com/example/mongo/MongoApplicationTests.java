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

	private boolean cleaningAllBefore = true;

	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private DocumentTypeRepository documentTypeRepository;
	@Autowired
	private DocumentSavingService documentSavingService;

	@Test
	public void saveDocuments() {
		if(cleaningAllBefore) {
			documentRepository.deleteAll().block();
		}

		if(!documentTypeRepository.existsById("SimpleDocument").block()) {
			DocumentType documentType = new DocumentType();
			documentType.setTypeName("SimpleDocument");
			Map<String, String> pd = new HashMap<>();
			pd.put("ClientNumber", "String");
			documentType.setPropertyDefinitions(pd);
			documentTypeRepository.save(documentType).block();
		}

		String vid = UUID.randomUUID().toString();

		for(int i = 0; i < 1; i++) {
			String id = UUID.randomUUID().toString();
			Document document = new Document();
			document.setDocumentId(id);
			document.setVersionSeriesId(vid);
			document.setType("SimpleDocument");
			Map<String, String> sp = new HashMap<>();
			sp.put("ClientNumber", "eper" + i);
			document.setStringProperties(sp);
			documentSavingService.storeDocument(document).block();
			assertNotNull(documentRepository.findById(id).block());
		}

		vid = UUID.randomUUID().toString();

		for(int i = 0; i < 2; i++) {
			String id = UUID.randomUUID().toString();
			Document document = new Document();
			document.setDocumentId(id);
			document.setVersionSeriesId(vid);
			document.setType("SimpleDocument");
			Map<String, String> sp = new HashMap<>();
			sp.put("ClientNumber", "barack" + i);
			document.setStringProperties(sp);
			documentSavingService.storeDocument(document).block();
			assertNotNull(documentRepository.findById(id).block());
		}

		vid = UUID.randomUUID().toString();

		for(int i = 0; i < 3; i++) {
			String id = UUID.randomUUID().toString();
			Document document = new Document();
			document.setDocumentId(id);
			document.setVersionSeriesId(vid);
			document.setType("SimpleDocument");
			Map<String, String> sp = new HashMap<>();
			sp.put("ClientNumber", "alma" + i);
			document.setStringProperties(sp);
			documentSavingService.storeDocument(document).block();
			assertNotNull(documentRepository.findById(id).block());
		}

		System.out.println(documentRepository.findByVersionSeriesIdOrderByCreationDate(vid).last().block());
	}
}
