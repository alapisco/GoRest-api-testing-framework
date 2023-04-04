package gorest.api.tests;

import gorest.api.models.Post;
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
public class PostsTests extends BaseTest {

    private static User user;
    private static Post post;
    private static final Logger logger = LogManager.getLogger(PostsTests.class);

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
        post = loadPostData("src/test/resources/post_data.json");
        post.setUserId(userId);
    }

    /**
     * Test creating a new post assigned to the user created on setup.
     */
    @DisplayName("Create new post")
    @Test
    @Order(1)
    public void createNewPost() {
        logger.info("createNewPost");
        Response response = given()
                .body(post)
                .when()
                .post("/public/v2/posts")
                .then()
                .statusCode(201).assertThat()
                .body("user_id", equalTo(user.getId()))
                .body("title", equalTo(post.getTitle()))
                .body("body", equalTo(post.getBody()))
                .body("id", notNullValue())
                .extract()
                .response();

        // Store the created user ID for future tests
        int postId = response.jsonPath().getInt("id");
        post.setId(postId);
    }

    /**
     * Test retrieving the newly created post.
     */
    @DisplayName("Get new post")
    @Test
    @Order(2)
    public void getNewPost() {
        logger.info("getNewUser");
        // Retrieve the created post by ID
        given()
                .when()
                .get("/public/v2/posts/" + post.getId())
                .then()
                .statusCode(200).assertThat()
                .body("user_id", equalTo(user.getId()))
                .body("title", equalTo(post.getTitle()))
                .body("body", equalTo(post.getBody()))
                .body("id",equalTo(post.getId()));
    }

    /**
     * Test updating the created post with a PUT request.
     */
    @DisplayName("Update created new post - PUT")
    @Test
    @Order(3)
    public void updateCreatedPost() {
        logger.info("updateCreatedPost");
        // Update post details
        post.setTitle("New Tile");
        post.setBody("New Body");

        // Send PUT request to update the post
        given()
                .body(post)
                .when()
                .put("/public/v2/posts/" + post.getId())
                .then()
                .statusCode(200).assertThat()
                .body("user_id", equalTo(user.getId()))
                .body("title", equalTo(post.getTitle()))
                .body("body", equalTo(post.getBody()))
                .body("id",equalTo(post.getId()));
    }

    /**
     * Test updating the created post with a PATCH request.
     */

    @DisplayName("Update created new post - PATCH")
    @Test
    @Order(4)
    public void updateCreatedPostPatch() {
        logger.info("updateCreatedPostPatch");
        // Update post details
        post.setTitle("Another New Title");
        post.setBody("Another new body");

        // Send PATCH request to update the post
        given()
                .body(post)
                .when()
                .patch("/public/v2/posts/" + post.getId())
                .then()
                .statusCode(200).assertThat()
                .body("user_id", equalTo(user.getId()))
                .body("title", equalTo(post.getTitle()))
                .body("body", equalTo(post.getBody()))
                .body("id",equalTo(post.getId()));
    }

    /**
     * Test deleting the created post.
     */
    @DisplayName("Delete new post")
    @Test
    @Order(5)
    public void deleteCreatedPost() {
        logger.info("deleteCreatedPost");
        // Send DELETE request to remove the post
        given()
                .when()
                .delete("/public/v2/posts/" + post.getId())
                .then()
                .statusCode(204);
    }

    /**
     * Test retrieving the deleted post.
     */
    @DisplayName("Get deleted post")
    @Test
    @Order(6)
    public void getDeletedPost() {
        logger.info("getDeletedPost");
        // Attempt to retrieve the deleted user
        given()
                .when()
                .get("/public/v2/posts/" + post.getId())
                .then()
                .statusCode(404).assertThat()
                .body("message", equalTo("Resource not found"));
    }

}