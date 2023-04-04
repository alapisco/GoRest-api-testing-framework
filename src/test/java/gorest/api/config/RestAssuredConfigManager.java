package gorest.api.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.PrintStream;

public class RestAssuredConfigManager {
    private final Configuration configuration;

    public RestAssuredConfigManager(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setupRestAssured() {
        RestAssured.baseURI = configuration.getBaseUrl();
        RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(LogDetail.ALL, true, new PrintStream(System.out));
        ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(LogDetail.ALL, true, new PrintStream(System.out));
        RestAssured.filters(requestLoggingFilter, responseLoggingFilter, new AllureRestAssured());
    }

    public RequestSpecification getDefaultRequestSpec() {
        System.out.println("Authorization Bearer " + configuration.getToken());

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + configuration.getToken())
                //.addFilter(new AllureRestAssured())
                .build();
    }
}
