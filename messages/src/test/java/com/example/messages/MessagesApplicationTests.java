package com.example.messages;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessagesApplicationTests {

	@Autowired
	private MessageSenderService senderService;

	@Test
	public void sendTest() {
		String vid = UUID.randomUUID().toString();
		String id = UUID.randomUUID().toString();
		Document document = new Document();
		document.setDocumentId(id);
		document.setVersionSeriesId(vid);
		document.setType("SimpleDocument");
		Map<String, String> sp = new HashMap<>();
		sp.put("ClientNumber", "barack12");
		document.setStringProperties(sp);
		senderService.send(document);
	}
}
