package com.app.quantitymeasurement.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ApplicationConfig {

	private static final Logger logger = Logger.getLogger(ApplicationConfig.class.getName());
	private static ApplicationConfig instance;
	private Properties properties;
	private Environment environment;

	public enum Environment {
		DEVELOPMENT, TESTING, PRODUCTION
	}

	public enum ConfigKey {
		REPOSITORY_TYPE("repository.type"), DB_DRIVER_CLASS("db.driver"), DB_URL("db.url"), DB_USERNAME("db.username"),
		DB_PASSWORD("db.password"),
		HIKARI_MAX_POOL_SIZE("db.hikari.maximum-pool-size"), HIKARI_MIN_IDLE("db.hikari.minimum-idle"),
		HIKARI_CONNECTION_TIMEOUT("db.hikari.connection-timeout"), HIKARI_IDLE_TIMEOUT("db.hikari.idle-timeout"),
		HIKARI_MAX_LIFETIME("db.hikari.max-lifetime"), HIKARI_POOL_NAME("db.hikari.pool-name"),
		HIKARI_CONNECTION_TEST_QUERY("db.hikari.connection-test-query");

		private final String key;

		ConfigKey(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}
	}

	private ApplicationConfig() {
		loadConfiguration();
	}

	public static synchronized ApplicationConfig getInstance() {
		if (instance == null) {
			instance = new ApplicationConfig();
		}
		return instance;
	}

	private void loadConfiguration() {
		properties = new Properties();
		try {

			InputStream input = ApplicationConfig.class.getClassLoader().getResourceAsStream("application.properties");
			if (input != null) {
				properties.load(input);
				logger.info("Configuration loaded successfully");
			} else {
				loadDefaults();
			}
			String env = properties.getProperty("app.env", "development");
			environment = Environment.valueOf(env.toUpperCase());
		} catch (Exception e) {
			logger.warning("Error loading configuration: " + e.getMessage());
			loadDefaults();
		}
	}

	private void loadDefaults() {
		properties.setProperty("repository.type", "cache");
		properties.setProperty("db.url", "jdbc:h2:./quantitymeasurementdb");
		properties.setProperty("db.username", "sa");
		properties.setProperty("db.password", "");
		environment = Environment.DEVELOPMENT;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public int getIntProperty(String key, int defaultValue) {
		try {
			return Integer.parseInt(properties.getProperty(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getEnvironment() {
		return environment.name();
	}

	public void printAllProperties() {
		logger.info("Loaded Configuration:");
		for (Object key : properties.keySet()) {
			logger.info(key + " = " + properties.get(key));
		}
	}
}