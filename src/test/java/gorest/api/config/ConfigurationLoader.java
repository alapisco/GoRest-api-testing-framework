package gorest.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;

/**
 * Loads the configuration settings for the application.
 */
public class ConfigurationLoader {

    private static ConfigurationLoader instance;
    private Configuration configuration;

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private ConfigurationLoader() {
        loadConfiguration();
    }

    /**
     * Gets the instance of ConfigurationLoader.
     *
     * @return the ConfigurationLoader instance
     */
    public static ConfigurationLoader getInstance() {
        if (instance == null) {
            instance = new ConfigurationLoader();
        }
        return instance;
    }

    /**
     * Loads the configuration from the 'config.yaml' file and sets the Configuration instance.
     */
    private void loadConfiguration() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yaml");

        if (inputStream != null) {
            try {
                configuration = mapper.readValue(inputStream, Configuration.class);

                if (configuration.getBaseUrl() == null || configuration.getBaseUrl().isEmpty()) {
                    throw new RuntimeException("base_url is missing in config.yaml");
                }

                if (configuration.getToken() == null || configuration.getToken().isEmpty()) {
                    throw new RuntimeException("token is missing in config.yaml");
                }
            } catch (Exception e) {
                throw new RuntimeException("Error loading config.yaml: " + e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("config.yaml not found");
        }
    }

    /**
     * Gets the Configuration instance.
     *
     * @return the Configuration instance
     */
    public Configuration getConfiguration() {
        return configuration;
    }
}
