package com.example.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class SqlTests {

	@Autowired
	DocumentRepository repo;
	DocumentService documentService;

	@Before
	public void setUp() {
		documentService = new DocumentService(repo, new NotificationSender() {
			@Override
			public void sendSaveSuccess(String id) {
				assertNotNull(id);
			}
			@Override
			public void sendUpdateSuccess(String id) {
				assertNotNull(id);
			}
		});
		repo.deleteAll();
	}

	@Test
	public void testSaving() {
		Document d = new Document();
		Document d2 = new Document();
		d.setDocumentId("0001");
		d.setTitle("Test1");
		d2.setDocumentId("0002");
		d2.setTitle("Test2");

		Map<String, String> sp = new HashMap<>();
		sp.put("ClientNumber", "123");
		sp.put("Author", "Teszt Elek");
		d.setProperties(sp);

		Map<String, String> sp2 = new HashMap<>();
		sp2.put("ClientNumber", "111");
		d2.setProperties(sp2);

		documentService.saveDocument(d);
		documentService.saveDocument(d2);

		Optional<Document> od1 = repo.findById(d.getDocumentId());
		Optional<Document> od2 = repo.findById(d2.getDocumentId());

		assertTrue(od1.isPresent());
		assertTrue(od2.isPresent());

		assertNotNull(od1.get().getProperties());
		assertNotNull(od2.get().getProperties());

		assertTrue(od1.get().getProperties().size() == 2);
		assertTrue(od2.get().getProperties().size() == 1);

		assertEquals("123", od1.get().getProperties().get("ClientNumber"));
		assertEquals("111", od2.get().getProperties().get("ClientNumber"));
	}

	@Test
	public void testUpdate() {
		Document d = new Document();
		d.setDocumentId("0003");
		d.setTitle("Test3");
		documentService.saveDocument(d);
		Optional<Document> od1 = repo.findById(d.getDocumentId());
		assertTrue(od1.isPresent());
		assertTrue(od1.get().getVersion() == 0);

		d.setTitle("NewTest3");
		documentService.updateDocument(d);
		od1 = repo.findById(d.getDocumentId());
		assertTrue(od1.isPresent());
		assertTrue(od1.get().getVersion() == 1);
	}
}
