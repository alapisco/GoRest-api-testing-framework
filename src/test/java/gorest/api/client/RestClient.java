package gorest.api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * RestClient class provides methods to perform API calls using Rest Assured library.
 */
public class RestClient {

    /**
     * Performs a GET request with the given path and query parameters.
     *
     * @param path          The path of the API endpoint.
     * @param queryParams   A map of query parameters.
     * @param authorization The authorization header value (Bearer token).
     * @return The API response.
     */
    public static Response get(String path, Map<String, ?> queryParams, String authorization) {
        return givenRequest(authorization)
                .queryParams(queryParams)
                .get(path);
    }

    /**
     * Performs a POST request with the given path, query parameters, and request body.
     *
     * @param path          The path of the API endpoint.
     * @param queryParams   A map of query parameters.
     * @param requestBody   The request body as an object.
     * @param authorization The authorization header value (Bearer token).
     * @return The API response.
     */
    public static Response post(String path, Map<String, ?> queryParams, Object requestBody, String authorization) {
        return givenRequest(authorization)
                .queryParams(queryParams)
                .body(requestBody)
                .post(path);
    }

    /**
     * Performs a PUT request with the given path, query parameters, and request body.
     *
     * @param path          The path of the API endpoint.
     * @param queryParams   A map of query parameters.
     * @param requestBody   The request body as an object.
     * @param authorization The authorization header value (Bearer token).
     * @return The API response.
     */
    public static Response put(String path, Map<String, ?> queryParams, Object requestBody, String authorization) {
        return givenRequest(authorization)
                .queryParams(queryParams)
                .body(requestBody)
                .put(path);
    }

    /**
     * Performs a DELETE request with the given path and query parameters.
     *
     * @param path          The path of the API endpoint.
     * @param queryParams   A map of query parameters.
     * @param authorization The authorization header value (Bearer token).
     * @return The API response.
     */
    public static Response delete(String path, Map<String, ?> queryParams, String authorization) {
        return givenRequest(authorization)
                .queryParams(queryParams)
                .delete(path);
    }

    /**
     * Creates a RequestSpecification instance with the provided authorization header value.
     *
     * @param authorization The authorization header value (Bearer token).
     * @return A RequestSpecification instance.
     */
    private static RequestSpecification givenRequest(String authorization) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + authorization)
                .contentType("application/json");
    }
}
