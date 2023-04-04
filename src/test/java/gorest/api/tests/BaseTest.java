package gorest.api.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import gorest.api.config.Configuration;
import gorest.api.config.ConfigurationLoader;
import gorest.api.config.RestAssuredConfigManager;
import gorest.api.models.Post;
import gorest.api.models.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;

/**
 The BaseTest class provides a foundation for all API tests in the gorest.api.tests package.
 It includes common setup and utility methods for loading test data.
 */
public class BaseTest {
    private Configuration config = ConfigurationLoader.getInstance().getConfiguration();
    private RestAssuredConfigManager configManager = new RestAssuredConfigManager(config);

    // Flag to prevent RestAssured from being configured multiple times
    private static boolean setupDone = false;

    /**
     * This method sets up RestAssured configuration for the test suite.
     * It runs once for all tests in the class.
     *
     * @throws Exception if any errors occur during setup
     */
    @BeforeAll
    public void setup() throws Exception {
        if (!setupDone) {
            configManager.setupRestAssured();
            RestAssured.requestSpecification = configManager.getDefaultRequestSpec();
            setupDone = true;
        }
    }

    /**
     * This method loads user test data from the specified JSON file.
     *
     * @param userDataPath the path of the JSON file containing user test data
     * @return a User object containing the loaded data
     * @throws IOException if any errors occur during file reading
     */
    public User loadUserData(String userDataPath) throws IOException {
        // Load test data from the JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(userDataPath), User.class);
    }

    /**
     * This method loads post test data from the specified JSON file.
     *
     * @param postDataPath the path of the JSON file containing post test data
     * @return a Post object containing the loaded data
     * @throws IOException if any errors occur during file reading
     */
    public Post loadPostData(String postDataPath) throws IOException {
        // Load test data from the JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(postDataPath), Post.class);
    }
}