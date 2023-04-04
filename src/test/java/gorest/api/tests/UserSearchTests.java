package gorest.api.tests;

import gorest.api.config.Configuration;
import gorest.api.config.ConfigurationLoader;
import gorest.api.models.User;
import gorest.api.utils.UniqueEmailGenerator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("User Search Tests")
public class UserSearchTests extends BaseTest {

    private Configuration config = ConfigurationLoader.getInstance().getConfiguration();
    private final Logger logger = LogManager.getLogger(UserSearchTests.class);

    private User user;

    @BeforeAll
    public void setup() throws Exception {
        super.setup();
        logger.info("Starting UserSearchTest");
        // create a new user
        user = loadUserData("src/test/resources/new_user_data.json");
        user.setEmail(UniqueEmailGenerator.generateUniqueEmail());

        // create new user
        Response response =
                given()
                        .body(user)
                        .post("/public/v2/users").
                        then()
                        .extract()
                        .response();
        int userId = response.jsonPath().getInt("id");
        user.setId(userId);
    }

    @DisplayName("Test search by name")
    @Test
    public void testSearchByName() {
        logger.info("testSearchByName");
        String searchName = user.getName();

        given()
                .contentType(ContentType.JSON)
                .queryParam("name", searchName)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", greaterThan(0))
                .body("", everyItem(hasKey("name")))
                .body("name", everyItem(containsStringIgnoringCase(searchName)));
    }

    @DisplayName("Test search by male gender")
    @Test
    public void testSearchByMaleGender() {
        logger.info("testSearchByMaleGender");
        String gender = "male";

        given()
                .contentType(ContentType.JSON)
                .queryParam("gender", gender)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", greaterThan(0))
                .body("", everyItem(hasKey("gender")))
                .body("gender", everyItem(containsStringIgnoringCase(gender)));
    }

    @DisplayName("Test search by female gender")
    @Test
    public void testSearchByFemaleGender() {
        logger.info("testSearchByFemaleGender");
        String gender = "female";

        given()
                .contentType(ContentType.JSON)
                .queryParam("gender", gender)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", greaterThan(0))
                .body("", everyItem(hasKey("gender")))
                .body("gender", everyItem(containsStringIgnoringCase(gender)));
    }

    @DisplayName("Test search by id")
    @Test
    public void testSearchById() {
        logger.info("testSearchById");
        String id = user.getId().toString();

        given()
                .contentType(ContentType.JSON)
                .queryParam("id", id)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", greaterThan(0))
                .body("", everyItem(hasKey("id")))
                .body("id", everyItem(equalTo(user.getId())));
    }

    @DisplayName("Test search by email")
    @Test
    public void testSearchByEmail() {
        logger.info("testSearchByEmail");
        String email = user.getEmail();

        given()
                .contentType(ContentType.JSON)
                .queryParam("email", email)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", greaterThan(0))
                .body("", everyItem(hasKey("email")))
                .body("email", everyItem(containsStringIgnoringCase(email)));
    }

    @DisplayName("Test search by active status")
    @Test
    public void testSearchByActiveStatus() {
        logger.info("testSearchByActiveStatus");
        String status = "active";

        given()
                .contentType(ContentType.JSON)
                .queryParam("status", status)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", greaterThan(0))
                .body("", everyItem(hasKey("status")))
                .body("status", everyItem(containsStringIgnoringCase(status)));
    }

    @DisplayName("Test search by inactive status")
    @Test
    public void testSearchByInActiveStatus() {
        logger.info("testSearchByInActiveStatus");
        String status = "inactive";

        given()
                .contentType(ContentType.JSON)
                .queryParam("status", status)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", greaterThan(0))
                .body("", everyItem(hasKey("status")))
                .body("status", everyItem(containsStringIgnoringCase(status)));
    }

    @DisplayName("Test search by multiple fields")
    @Test
    public void testSearchByMultipleFields() {
        logger.info("testSearchByMultipleFields");

        given()
                .contentType(ContentType.JSON)
                .queryParam("status", "active")
                .queryParam("gender", "male")
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", greaterThan(0))
                .body("", everyItem(hasKey("status")))
                .body("status", everyItem(containsStringIgnoringCase("active")))
                .body("gender", everyItem(containsStringIgnoringCase("male")));
    }

    @DisplayName("Test search with non-existent value")
    @Test
    public void testSearchWithNonExistentValue() {
        logger.info("testSearchWithNonExistentValue");
        String nonExistentName = "This is a nonExistentName1234";

        given()
                .contentType(ContentType.JSON)
                .queryParam("name", nonExistentName)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", equalTo(0));
    }

    @DisplayName("Test search with invalid email")
    @Test
    public void testSearchWithInvalidEmail() {
        logger.info("testSearchWithInvalidEmail");
        String invalidEmail = "notanemail";

        given()
                .contentType(ContentType.JSON)
                .queryParam("email", invalidEmail)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", equalTo(0));
    }

    @DisplayName("Test search with invalid gender")
    @Test
    public void testSearchWithInvalidGender() {
        logger.info("testSearchWithInvalidGender");
        String invalidGender = "invalid_gender";

        given()
                .contentType(ContentType.JSON)
                .queryParam("gender", invalidGender)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", equalTo(0));
    }

    @DisplayName("Test search with invalid status")
    @Test
    public void testSearchWithInvalidStatus() {
        logger.info("testSearchWithInvalidStatus");
        String invalidStatus = "invalid_status";

        given()
                .contentType(ContentType.JSON)
                .queryParam("status", invalidStatus)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", equalTo(0));
    }

    @DisplayName("Test search with invalid id")
    @Test
    public void testSearchWithInvalidId() {
        logger.info("testSearchWithInvalidId");
        String invalidId = "invalid_id";

        given()
                .contentType(ContentType.JSON)
                .queryParam("id", invalidId)
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", equalTo(0));
    }
}
