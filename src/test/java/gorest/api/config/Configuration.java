package gorest.api.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the configuration settings for the application.
 */
public class Configuration {

    @JsonProperty("base_url")
    private String baseUrl;

    @JsonProperty("token")
    private String token;

    /**
     * Gets the base URL for the API.
     *
     * @return the base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Sets the base URL for the API.
     *
     * @param baseUrl the base URL
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Gets the authentication token for the API.
     *
     * @return the authentication token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the authentication token for the API.
     *
     * @param token the authentication token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
