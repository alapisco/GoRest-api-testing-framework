package gorest.api.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@DisplayName("User Pagination Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserPaginationTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(UserPaginationTests.class);

    @BeforeAll
    public void setup() throws Exception {
        super.setup();
    }

    @DisplayName("Test default pagination values")
    @Test
    public void testDefaultPaginationValues() {
        logger.info("Starting testDefaultPaginationValues");

        int expectedDefaultPerPage = 10;

        given()
                .contentType(ContentType.JSON)
                .when()
                .request("GET", "/public/v2/users")
                .then()
                .statusCode(200)
                .header("X-Pagination-Limit", equalTo(String.valueOf(expectedDefaultPerPage)))
                .body("size()", lessThanOrEqualTo(expectedDefaultPerPage));
    }

    @DisplayName("Test valid pagination values")
    @Test
    public void testValidPaginationValues() {
        logger.info("Starting testValidPaginationValues");

        int page = 2;
        int perPage = 10;

        given()
                .contentType(ContentType.JSON)
                .queryParam("page", page)
                .queryParam("per_page", perPage)
                .when()
                .request("GET", "/public/v2/users")
                .then()
                .statusCode(200)
                .header("X-Pagination-Page", equalTo(String.valueOf(page)))
                .header("X-Pagination-Limit", equalTo(String.valueOf(perPage)))
                .body("size()", lessThanOrEqualTo(perPage));
    }

    @DisplayName("Test max per page value")
    @Test
    public void testMaxPerPageValue() {
        logger.info("Starting testMaxPerPageValue");

        int maxPerPage = 100;

        given()
                .contentType(ContentType.JSON)
                .queryParam("per_page", maxPerPage)
                .when()
                .request("GET", "/public/v2/users")
                .then()
                .statusCode(200)
                .body("size()", lessThanOrEqualTo(maxPerPage));
    }

    @DisplayName("Test out of range page value")
    @Test
    public void testOutOfRangePageValue() {
        logger.info("Starting testOutOfRangePageValue");

        int outOfRangePage = 99999; // Assuming page 99999 is out of range

        given()

                .contentType(ContentType.JSON)
                .queryParam("page", outOfRangePage)
                .when()
                .request("GET", "/public/v2/users")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0)); // Expecting an empty result set for an out of range page
    }

    @DisplayName("Test negative or invalid values")
    @Test
    public void testNegativeOrInvalidValues() {
        logger.info("Starting testNegativeOrInvalidValues");

        int negativePage = -1;
        int negativePerPage = -5;

        given()
                .contentType(ContentType.JSON)
                .queryParam("page", negativePage)
                .queryParam("per_page", negativePerPage)
                .when()
                .request("GET", "/public/v2/users")
                .then()
                .header("X-Pagination-Page", equalTo(String.valueOf("1")))
                .header("X-Pagination-Limit", equalTo(String.valueOf("10"))); // Expecting a bad request status code due to negative/invalid values

        logger.info("TEST PASSED");
    }

    @DisplayName("Test pagination headers")
    @Test
    public void testPaginationHeaders() {
        logger.info("Starting testPaginationHeaders");

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .request("GET", "/public/v2/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        int totalPages = Integer.parseInt(response.getHeader("X-Pagination-Pages"));
        int totalItems = Integer.parseInt(response.getHeader("X-Pagination-Total"));
        int currentPage = Integer.parseInt(response.getHeader("X-Pagination-Page"));
        int itemsPerPage = Integer.parseInt(response.getHeader("X-Pagination-Limit"));

        // Check the headers' values
        Assertions.assertTrue(totalPages >= 0, "Total pages should be greater than or equal to zero");
        Assertions.assertTrue(totalItems >= 0, "Total items should be greater than or equal to zero");
        Assertions.assertTrue(currentPage == 1, "Current page should be greater than or equal to zero");
        Assertions.assertTrue(itemsPerPage == 10, "Items per page should be greater than or equal to zero");

    }



}
