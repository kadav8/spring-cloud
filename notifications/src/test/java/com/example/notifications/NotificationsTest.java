package com.example.notifications;

import static com.example.EnvironmentSetter.setEnvProperties;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationsTest {

	@BeforeClass
	public static void init() {
		setEnvProperties();
	}

	@Test
	public void contextLoads() {
	}

}
