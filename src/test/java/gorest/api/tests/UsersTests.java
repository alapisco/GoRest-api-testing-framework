package gorest.api.tests;

import gorest.api.models.User;
import gorest.api.utils.UniqueEmailGenerator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Test class for User-related API operations.
 */
@DisplayName("Users Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersTests extends BaseTest {

    private static User user;
    private static final Logger logger = LogManager.getLogger(UsersTests.class);

    /**
     * Set up the test environment.
     * Load user data and generate a unique email for the user.
     *
     * @throws Exception if setup fails
     */
    @BeforeAll
    public void setup() throws Exception {
        super.setup();
        user = loadUserData("src/test/resources/new_user_data.json");
        user.setEmail(UniqueEmailGenerator.generateUniqueEmail());
    }

    /**
     * Test creating a new user.
     */
    @DisplayName("Create new user")
    @Test
    @Order(1)
    public void createNewUser() {
        logger.info("createNewUser");
        Response response = given()
                .body(user)
                .when()
                .post("/public/v2/users")
                .then()
                .statusCode(201).assertThat()
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()))
                .body("gender", equalTo(user.getGender()))
                .body("status", equalTo(user.getStatus()))
                .body("id", notNullValue())
                .extract()
                .response();

        // Store the created user ID for future tests
        int userId = response.jsonPath().getInt("id");
        user.setId(userId);
    }

    /**
     * Test retrieving the newly created user.
     */
    @DisplayName("Get new user")
    @Test
    @Order(2)
    public void getNewUser() {
        logger.info("getNewUser");
        // Retrieve the created user by ID
        given()
                .when()
                .get("/public/v2/users/" + user.getId())
                .then()
                .statusCode(200).assertThat()
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()))
                .body("gender", equalTo(user.getGender()))
                .body("status", equalTo(user.getStatus()))
                .body("id", equalTo(user.getId()));
    }

    /**
     * Test updating the created user with a PUT request.
     */
    @DisplayName("Update created new user - PUT")
    @Test
    @Order(3)
    public void updateCreatedUser() {
        logger.info("updateCreatedUser");
        // Update user details
        user.setName("New Name");
        user.setEmail(UniqueEmailGenerator.generateUniqueEmail());
        user.setGender("female");
        user.setStatus("inactive");

        // Send PUT request to update the user
        given()
                .body(user)
                .when()
                .put("/public/v2/users/" + user.getId())
                .then()
                .statusCode(200).assertThat()
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()))
                .body("gender", equalTo(user.getGender()))
                .body("status", equalTo(user.getStatus()))
                .body("id", equalTo(user.getId()));
    }

/**
 * Test updating the created user with a PATCH request.
 */

@DisplayName("Update created new user - PATCH")
@Test
@Order(4)
public void updateCreatedUserPatch() {
    logger.info("updateCreatedUserPatch");
    // Update user details
    user.setName("Another New Name");
    user.setEmail(UniqueEmailGenerator.generateUniqueEmail());
    user.setGender("male");
    user.setStatus("active");

    // Send PATCH request to update the user
    given()
            .body(user)
            .when()
            .patch("/public/v2/users/" + user.getId())
            .then()
            .statusCode(200).assertThat()
            .body("name", equalTo(user.getName()))
            .body("email", equalTo(user.getEmail()))
            .body("gender", equalTo(user.getGender()))
            .body("status", equalTo(user.getStatus()))
            .body("id", equalTo(user.getId()));
}

    /**
     * Test deleting the created user.
     */
    @DisplayName("Delete new user")
    @Test
    @Order(5)
    public void deleteCreatedUser() {
        logger.info("deleteCreatedUser");
        // Send DELETE request to remove the user
        given()
                .when()
                .delete("/public/v2/users/" + user.getId())
                .then()
                .statusCode(204);
    }

    /**
     * Test retrieving the deleted user.
     */
    @DisplayName("Get deleted user")
    @Test
    @Order(6)
    public void getDeletedUser() {
        logger.info("getDeletedUser");
        // Attempt to retrieve the deleted user
        given()
                .when()
                .get("/public/v2/users/" + user.getId())
                .then()
                .statusCode(404).assertThat()
                .body("message", equalTo("Resource not found"));
    }

    @DisplayName("Create user with invalid email")
    @Test
    public void createUserWithInvalidEmail() {
        logger.info("createUserWithInvalidEmail");
        User userWithInvalidEmail = new User();
        userWithInvalidEmail.setName("Name");
        userWithInvalidEmail.setEmail("InvalidEmail");
        userWithInvalidEmail.setGender("female");
        userWithInvalidEmail.setStatus("inactive");

        given()
                .body(userWithInvalidEmail)
                .when()
                .post("/public/v2/users")
                .then()
                .statusCode(422)
                .assertThat()
                .body("[0].field", equalTo("email"))
                .body("[0].message", equalTo("is invalid"));
    }

    @DisplayName("Create user with invalid gender")
    @Test
    public void createUserWithInvalidGender() {
        logger.info("createUserWithInvalidGender");
        User userWithInvalidGender = new User();
        userWithInvalidGender.setName("Name");
        userWithInvalidGender.setEmail(UniqueEmailGenerator.generateUniqueEmail());
        userWithInvalidGender.setGender("invalid_gender");
        userWithInvalidGender.setStatus("inactive");

        given()
                .body(userWithInvalidGender)
                .when()
                .post("/public/v2/users")
                .then()
                .statusCode(422)
                .assertThat()
                .body("[0].field", equalTo("gender"))
                .body("[0].message", equalTo("can't be blank, can be male of female"));
    }

}