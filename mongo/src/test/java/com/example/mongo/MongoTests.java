package com.example.mongo;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.EnvSetter;

@RunWith(SpringRunner.class)
@DataMongoTest//(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class MongoTests {

	boolean cleaningAllBefore = true;
	boolean cleaningAllAfter = true;
	boolean cleaning = true;

	@Autowired
	DocumentRepository documentRepository;
	DocumentService documentSavingService;
	List<String> toDelete = new ArrayList<>();

	@BeforeClass
	public static void init() {
		EnvSetter.setEnvProperties();
	}

	@Before
	public void setUp() {
		documentSavingService = new DocumentService(documentRepository, new NotificationSender() {
			@Override
			public void sendSaveSuccess(String id) {
				assertNotNull(id);
			}
			@Override
			public void sendUpdateSuccess(String id) {
				assertNotNull(id);
			}
		});
		if (cleaningAllBefore) {
			documentRepository.deleteAll().block();
		} else if (cleaning) {
			for (String id : toDelete) {
				documentRepository.deleteById(id).block();
			}
		}
	}

	@After
	public void tearDown() {
		if (cleaningAllAfter) {
			documentRepository.deleteAll().block();
		} else if (cleaning) {
			for (String id : toDelete) {
				documentRepository.deleteById(id).block();
			}
		}
	}

	@Test
	public void testSaving() {
		save(documentSavingService, "eper");
		save(documentSavingService, "barack");
	}

	@Test
	public void testUpdate() {
		Document d = save(documentSavingService, "alma");
		Document d2 = update(documentSavingService, "alma2", d);
		update(documentSavingService, "alma3", d2);
	}

	private Document save(DocumentService documentSavingService, String clientNumber) {
		String id = UUID.randomUUID().toString();
		Document document = new Document();
		document.setDocumentId(id);
		Map<String, String> sp = new HashMap<>();
		sp.put("ClientNumber", clientNumber);
		document.setProperties(sp);
		documentSavingService.saveDocument(document).block();
		assertNotNull(documentRepository.findById(id).block());
		toDelete.add(id);
		return documentRepository.findById(id).block();
	}

	private Document update(DocumentService documentSavingService, String clientNumber, Document old) {
		Document document = new Document();
		document.setDocumentId(old.getDocumentId());
		document.setVersion(old.getVersion());
		document.setCreationDate(old.getCreationDate());
		Map<String, String> sp = new HashMap<>();
		sp.put("ClientNumber", clientNumber);
		document.setProperties(sp);
		documentSavingService.updateDocument(document).block();
		assertNotNull(documentRepository.findById(old.getDocumentId()).block());
		return documentRepository.findById(old.getDocumentId()).block();
	}
}
