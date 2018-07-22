package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class EnvironmentSetter {

	public static void setEnvProperties() {
		if (new File("../.env").exists()) {
			try (InputStream input = new FileInputStream(new File("../.env"))) {
				Properties prop = new Properties();
				prop.load(input);
				for (Object key : prop.keySet()) {
					if(!key.toString().startsWith("#")) {
						System.setProperty(key.toString(), prop.get(key).toString());
					}
				}
			} catch (Throwable t) {
			}
		}
		if (new File(".env").exists()) {
			try (InputStream input = new FileInputStream(new File(".env"))) {
				Properties prop = new Properties();
				prop.load(input);
				for (Object key : prop.keySet()) {
					if(!key.toString().startsWith("#")) {
						System.setProperty(key.toString(), prop.get(key).toString());
					}
				}
			} catch (Throwable t) {
			}
		}
	}
}
