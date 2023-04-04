# GoRest API Testing Framework

This is an implementation of the `QA Automation Test part 2` for `Ultra`

This is a Test Automation Framework to develop API tests for the `GoRest`API defined [here](https://gorest.co.in/)

The project structure is organized and easy to understand, with separate packages for configuration,
, models, utils, and tests.

It implements:

- CRUD operations on the users and post endpoints
- Functional and negative tests for pagination on the users endpoint
- Functional and negative tests for search on the users endpoint

Refer to the [Tests Section](#tests) for full details on how those testcases are implemented

## Table of Contents

1. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installing](#installing)
    - [Running the test](#running-the-tests)
    - [Test Results](#tests-results)
2. [Dependencies](#dependencies)
3. [API Testing Framework Design](#api-testing-framework-design)
    - [API Configuration Properties](#api-configuration-properties)
        - [Configuration class](#configuration)
        - [Configuration Loader](#configuration)
    - [RestAssured Configuration](#restassured-configuration)
        - [RestAssuredConfigurationManager](#restassuredconfigmanager)
    - [API Models](#api-models)
        - [Post](#post-model)
        - [User](#user-model)
    - [Utilities](#utilities)
        - [JsonDataReader](#jsondatareader)
        - [UniqueEmailGenerator](#uniqueemailgenerator)
    - [Tests]()
        - [BaseTest class](#basetest)
        - [PostTests](#poststests)
        - [UsersTests](#userstests)
        - [UserPaginationTests](#userpaginationtests)
        - [UsersTests](#usersearchtests)
    - [CI/CD Pipeline](#cicd-pipeline)
        - [GitHub Actions Configuration](#github-actions-configuration)
        - [Test Execution Reports](#test-execution-reports)
            - [Junit Reports](#junit-reports)
            - [Allure Reports](#allure-reports)

## Getting Started

These instructions will help you set up the project on your local machine for development and testing purposes.

### Prerequisites

- JDK 11 or higher
- Maven 3.6.0 or higher

### Installing

1. Clone the repository:
```
git clone https://github.com/your-username/saucedemo-ui-testing.git
```

2. Navigate to the project root directory:
```
cd saucedemo-ui-testing
```
3. Install the required dependencies:
```
mvn clean install
```

### Running the tests

To run the tests execute `mvn clean test`

### Tests Results

At the end of the execution you will get in console information about the tests executions.

The framework generates `Junit` and ` Allure html` reports with information of all the
`Requests` and `Responses` made on each test

#### Junit report

Junit will provide tests results summary in `console` and will also generate `xml files` located
in directory `target/surefire-reports`

#### Allure report

To see a more detailed `Allure html report`, after execution:

```
mvn allure:serve
```

## Dependencies

This project uses the following dependencies:

- **rest-assured**: REST Assured is a Java library that simplifies testing and validation of REST APIs. It provides a DSL (domain-specific language) for writing tests in a more readable and concise manner.
- **JUnit 5**: A testing framework for Java applications
- **Maven**: A build tool and dependency management system for Java projects
- **Allure** a test report tool for visual reports
- **Jackson Databind** a library that converts JSON to Java Objects
- **allure-maven**: Allure Maven is a plugin for Maven that generates Allure reports, which provide a clear and structured representation of test execution results.
- **allure-junit5**: This library provides integration between Allure and JUnit 5 to generate Allure reports from JUnit 5 test execution results.
- **json-path**: JSON Path is a Java library that provides a way to query JSON data using a JSONPath expression. It is used for extracting data from JSON responses.
- **log4j-core** and **log4j-api**: Log4j is a widely-used logging library for Java applications. It provides the API (log4j-api) and the core implementation (log4j-core) for logging functionality.

## API Testing Framework Design
This API testing framework helps you set up and execute tests for a RESTful API. It includes classes
for managing test configurations, such as the base URL and authentication token. The framework is
designed to be flexible and reusable, allowing you to focus on writing your test cases without
worrying about the underlying configuration management.



### API Configuration Properties

To use the API testing framework, first ensure that you have a config.yaml file with the necessary properties
in the src/test/resources directory of your project.

```yaml
base_url: https://your.api.base.url
token: your_api_token
```

This file is already provided with a working token in place

#### Configuration
The Configuration class represents the settings needed to access and interact with the API.
It includes `the base URL (baseUrl)` and an `authentication token (token)`.

This class provides getter
and setter methods for these properties, allowing you to read and modify them as needed.

#### ConfigurationLoader
The ConfigurationLoader class is responsible for loading the configuration settings from a YAML
file (`config.yaml`) and creating a Configuration instance with the loaded settings. This class
follows the Singleton pattern to ensure that only one instance of the `ConfigurationLoader` is
created and used throughout the application.

To load the configuration settings, the `ConfigurationLoader` uses the `Jackson library`, specifically
the `ObjectMapper` class and the `YAMLFactory`. It reads the `config.yam`l file from the classpath and
deserializes the contents into a `Configuration` instance.

If the `config.yaml` file is missing or the `base_url` and `token` properties are not specified,
the `ConfigurationLoader` throws a `RuntimeException`, signaling an error in the configuration
setup.

### RestAssured Configuration
#### RestAssuredConfigManager
The RestAssuredConfigManager class helps set up and configure the RestAssured library for API
testing. It uses the provided `Configuration instance` to configure the` base URL` and the
`authentication token` for API requests. Additionally, it sets up `logging` and reporting filters
`for Allure Reports` to capture the details of the API requests and responses.

The `setupRestAssured()` method configures the `base URI` for RestAssured, along with request and
response logging filters. It also adds the `AllureRestAssured` filter for integrating with the
`Allure reporting framework`.

The `getDefaultRequestSpec`() method creates a default `RequestSpecification` with the necessary
headers and settings, such as the `Content-Type` and `Authorization` header with the `Bearer token`.
This method is used to create a default request specification for all API tests.

### API Models

The User and Post model classes represent the objects of the users and posts endpoints in the API.

These classes provide a way to map the JSON response from the API to Java objects, making it
easier to work with the data in your test cases.

#### Post Model
The `Post` class represents a post object returned by the API. It contains properties for the
post's `id`, `user id`, `title`, and `body`.

#### User Model
The `User` class represents a user object returned by the API. It contains properties for the
user's `id`, `name`, `email`, `gender`, and `status`. The `@JsonIgnoreProperties` annotation is used to
ignore any unknown properties in the `JSON response`, making the class more `resilient` to
API changes.

### Utilities

#### JsonDataReader
The `JsonDataReader` utility class provides a convenient way to read JSON data from a file and
convert it into a Java Map representation. This utility is used to load test data or other
JSON resources during test execution.

#### UniqueEmailGenerator
The `UniqueEmailGenerator` utility class provides a simple way to generate unique email
addresses for the tests. This can be particularly useful when creating new user
accounts or other scenarios where unique email addresses are required.

### Tests Classes

#### BaseTest
The BaseTest class serves as a foundation for all API tests classes.

It configures RestAssured for the test suite and provides utility methods to load test
data from JSON files

#### PostsTests

The PostsTests class  contains a series of API tests focused on operations on the Post endpoint

It extends the BaseTest class to inherit common setup and utility methods

The class includes the following tests:

- `Create new post`: Tests the creation of a new post assigned to a user created during the setup
- `Get new post`: Tests retrieving the newly created post
- ` Update created new post` - PUT: Tests updating the created post with a PUT request
- `Update created new post` - PATCH: Tests updating the created post with a PATCH request
- `Delete new post`: Tests deleting the created post
- `Get deleted post`: Tests retrieving the deleted post
-
#### UserPaginationTests

The UserPaginationTests class contains a series of API tests focused on User pagination operations.

It extends the BaseTest class to inherit common setup and utility methods

The class includes the following tests:

- `Test default pagination values`: Tests the default pagination values returned by the API
- `Test valid pagination values`: Tests valid custom pagination values provided as query parameters
- `Test max per page value`: Tests the maximum allowed value for the per_page query parameter
- `Test out of range page value`: Tests an out of range value for the page query parameter
- `Test negative or invalid values`: Tests negative or invalid values for the page and per_page query parameters
- `Test pagination headers`: Tests the values of the pagination-related headers returned by the API

#### UserSearchTests
The UserSearchTests class contains a series
of API tests focused on User search operations.

Extends the BaseTest class to inherit common setup and utility methods

The class includes the following tests:

- `Test search by name`: Tests search functionality by name
- `Test search by male gender`: Tests search functionality by male gender
- `Test search by female gender`: Tests search functionality by female gender
- `Test search by id`: Tests search functionality by user ID
- `Test search by email`: Tests search functionality by email
- `Test search by active status`: Tests search functionality by active status
- `Test search by inactive status`: Tests search functionality by inactive status
- `Test search by multiple fields`: Tests search functionality by multiple fields
- `Test search with non-existent value`: Tests search functionality with a non-existent value
- `Test search with invalid email`: Tests search functionality with an invalid email
- `Test search with invalid gende`r: Tests search functionality with an invalid gender
- `Test search with invalid status`: Tests search functionality with an invalid status
- `Test search with invalid id`: Tests search functionality with an invalid ID

#### UsersTests

This a test suite for a REST API related to user operations .

The test class has several test methods for different
operations related to users, such as creating, retrieving, updating, and deleting a user.

The class includes the following tests:


- `createNewUser`: This test case verifies that a new user is created successfully by sending a POST request to the /public/v2/users endpoint. It asserts that the response status code is 201 (created) and that the response body contains the expected user information.

- `getNewUser`: This test case retrieves the newly created user by sending a GET request to /public/v2/users/{userId}. It asserts that the response status code is 200 (OK) and that the response body contains the expected user information.

- `updateCreatedUser`: This test case updates the created user using a PUT request to /public/v2/users/{userId}. It modifies the user's name, email, gender, and status, and asserts that the response status code is 200 (OK) and that the response body contains the updated user information.

- `updateCreatedUserPatch`: This test case updates the created user using a PATCH request to /public/v2/users/{userId}. Similar to the previous test, it modifies the user's name, email, gender, and status, and asserts that the response status code is 200 (OK) and that the response body contains the updated user information.

- `deleteCreatedUser`: This test case deletes the created user by sending a DELETE request to /public/v2/users/{userId}. It asserts that the response status code is 204 (No Content), which indicates that the user has been deleted successfully.

- `getDeletedUser`: This test case attempts to retrieve the deleted user by sending a GET request to /public/v2/users/{userId}. It asserts that the response status code is 404 (Not Found) and that the response body contains an appropriate error message.

- `createUserWithInvalidEmail`: This test case tries to create a user with an invalid email address. It sends a POST request to /public/v2/users and asserts that the response status code is 422 (Unprocessable Entity) and that the response body contains the appropriate error message.

- `createUserWithInvalidGender`: This test case tries to create a user with an invalid gender value. It sends a POST request to /public/v2/users and asserts that the response status code is 422 (Unprocessable Entity) and that the response body contains the appropriate error message.


### CI/CD pipeline

This framework is setup as part of step in a CI/CD pipeline.

It is configured and deployed in `Github Actions`. Using Github hosted runners.

Every time there is an update to the project or if requested manually. The framework will execute the test cases.

After the execution, a brief `junit report` will be shown in the Summary section, and also a more detailed html `Allure report`
will be deployed to `Github Pages`. This report contains screenshots of all the test cases executed, as well as other
test execution data.

To see the test results from the latest run go to:
[https://alapisco.github.io/sauce-test](https://alapisco.github.io/sauce-test)


#### GitHub Actions Configuration

The project includes workflow file `.github/workflow/api-tests.yml` which executes the framework and generates test reports.

In summary , the tests are run in `Ubuntu`, using `JDK11` and `maven`.

After the execution you can see` Junit summary results` and a visual `html Allure report`

```
Allure reports are deployed to the Github Pages of the reporitory
```

#### Test Execution Reports

To see all the executions, go to the `Actions` section of the repository:

[https://github.com/alapisco/sauce-test/actions](https://github.com/alapisco/sauce-test/actions)

In the `Workflows` section, on the left, select `UI Tests`. This is the flow that runs the workflows
that run the chrome and firefox tests.

On the left menu, at the `Jobs` section you will see `run-ui-tests-chrome` and  `run-ui-tests-firefox`

Click on each of them, and click the `build` section that will be expanded

On the right it will show the output of all the steps.
- Expand  `Run chrome tests` or `Run firefox tests` to see the output of the `mvn` command that runs the tests
- Expand `Report URL` to see where the `Allure report` is available

On the left you will see the `Summary` section that will show junit test results in the `Build Summary`
section

#### Junit reports

- Click the `Summary` section

- On the right, there will be a `Build Summary` section with the information of the tests passes/failed

#### Allure reports

To see the `Allure report` url:

- Click `build` inside the `Jobs` name section on the left menu
- On the right, in the `Build section`, expand the `Report URL` section. The url for the
  report of that execution will be shown there

```
The Allure reports take a minute or two to be deployed to Github Pages.

The URL might not be immediately available while the report is being deployed.
```
