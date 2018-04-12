package com.example.mongo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
public class DocumentSavingTests {

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
			save(documentSavingService, vid, "eper", i);
		}
		vid = UUID.randomUUID().toString();
		for(int i = 0; i < 2; i++) {
			save(documentSavingService, vid, "barack", i);
		}
		vid = UUID.randomUUID().toString();
		for(int i = 0; i < 3; i++) {
			save(documentSavingService, vid, "alma", i);
		}

		try {
			Document doc = documentRepository.findByVersionSeriesIdOrderByCreationDate(vid).last().block();
			doc.setDocumentId(null);
			doc.setVersionSeriesId(vid);
			documentSavingService.saveDocument(doc, Versioning.NEW).block();
			fail("Exception expexted");
		} catch(RuntimeException e) {
			System.out.println(e);
			assertTrue(e.toString().contains("VersionSeriesId already exists"));
		}

		try {
			Document doc = documentRepository.findByVersionSeriesIdOrderByCreationDate(vid).last().block();
			doc.setDocumentId(null);
			doc.setVersionSeriesId("nem létezik");
			documentSavingService.saveDocument(doc, Versioning.UPDATE).block();
			fail("Exception expexted");
		} catch(RuntimeException e) {
			System.out.println(e);
			assertTrue(e.toString().contains("VersionSeriesId not exists"));
		}

		try {
			Document doc = documentRepository.findByVersionSeriesIdOrderByCreationDate(vid).last().block();
			doc.setDocumentId(null);
			doc.setVersionSeriesId("nem létezik");
			documentSavingService.saveDocument(doc, Versioning.SAME).block();
			fail("Exception expexted");
		} catch(RuntimeException e) {
			System.out.println(e);
			assertTrue(e.toString().contains("VersionSeriesId not exists"));
		}

		try {
			Document doc = documentRepository.findByVersionSeriesIdOrderByCreationDate(vid).last().block();
			doc.setVersionSeriesId("nem létezik");
			documentSavingService.saveDocument(doc, Versioning.NEW).block();
			fail("Exception expexted");
		} catch(RuntimeException e) {
			System.out.println(e);
			assertTrue(e.toString().contains("DocumentId already exists"));
		}
	}

	@Test
	public void testSavingNoType() {
		DocumentSavingService documentSavingService = new DocumentSavingService(documentRepository, documentTypeRepository);
		String vid = UUID.randomUUID().toString();
		try {
			save(documentSavingService, vid, "eper12", "NincsIlyen", 0);
			fail("Exception expexted");
		} catch(RuntimeException e) {
			System.out.println(e);
			assertTrue(e.toString().contains("DocumentType not exists"));
		}
	}

	private void save(DocumentSavingService documentSavingService, String vid, String clientNumber, int i) {
		save(documentSavingService, vid, clientNumber, "SimpleDocument", i);
	}

	private void save(DocumentSavingService documentSavingService, String vid, String clientNumber, String docType, int i) {
		System.out.println("test save, vsid: " + vid);
		String id = UUID.randomUUID().toString();
		Document document = new Document();
		document.setDocumentId(id);
		document.setVersionSeriesId(vid);
		document.setType(docType);
		Map<String, String> sp = new HashMap<>();
		sp.put("ClientNumber", clientNumber + i);
		document.setStringProperties(sp);
		if(i > 0) {
			documentSavingService.saveDocument(document, Versioning.UPDATE).block();
		} else {
			documentSavingService.saveDocument(document, Versioning.NEW).block();
		}
		assertNotNull(documentRepository.findById(id).block());
	}
}
