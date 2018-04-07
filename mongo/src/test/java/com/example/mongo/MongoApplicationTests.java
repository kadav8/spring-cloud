package com.example.mongo;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.mongo.domain.Document;
import com.example.mongo.domain.DocumentType;
import com.example.mongo.domain.Versioning;
import com.example.mongo.repository.DocumentRepository;
import com.example.mongo.repository.DocumentTypeRepository;
import com.example.mongo.service.DocumentSavingService;

@RunWith(SpringRunner.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class MongoApplicationTests {

	boolean cleaningAllBefore = true;

	@Autowired
	DocumentRepository documentRepository;
	@Autowired
	DocumentTypeRepository documentTypeRepository;

	@Test
	public void testSaving() {
		DocumentSavingService documentSavingService = new DocumentSavingService(documentRepository, documentTypeRepository);

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
			documentSavingService.saveDocument(document, Versioning.NEW).block();
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
			documentSavingService.saveDocument(document, Versioning.NEW).block();
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
			documentSavingService.saveDocument(document, Versioning.NEW).block();
			assertNotNull(documentRepository.findById(id).block());
		}

		System.out.println(documentRepository.findByVersionSeriesIdOrderByCreationDate(vid).last().block());
	}
}
