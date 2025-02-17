package ca.op.lamda.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Configuration {
    INSTANCE;

    private final Properties properties = loadProperties();

    private Properties loadProperties() {
        final Properties prop = loadPropertiesFile("config.properties");

        return prop;
    }


    public Properties properties() {
        return this.properties;
    }


    public Properties loadPropertiesFile(String filePath) {
        Properties prop = new Properties();

        try (InputStream resourceAsStream = Configuration.class.getClassLoader().getResourceAsStream(filePath)) {
            prop.load(resourceAsStream);
        } catch (IOException e) {
            System.err.println("Unable to load properties file : " + filePath);
        }

        return prop;
    }

}