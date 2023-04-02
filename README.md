# GoRest Api Testing Framework

## Test Configuration 

In this testing framework, we use a configuration file to store the necessary settings for our API tests, 
such as the base URL and authentication token. 

### Configuration File
We use a YAML file format for our configuration file. YAML is a human-readable data serialization format that 
is easy to read and edit. 

The configuration file is named config.yaml, and it contains the following properties:

- `base_url`: The base URL for all the API endpoints.
- `token`: The authentication token, used as a Bearer token in the Authorization header for API requests.

Example config.yaml:

```yaml
base_url: https://gorest.co.in/public/v2
token: my_api_token
```


### Configuration Classes

We have two main classes for handling the configuration settings:

- Configuration: Represents the configuration settings for the application.
- ConfigurationLoader: Loads the configuration settings from the config.yaml file.

### Configuration Class
The Configuration class is a simple Java bean class that holds the properties of the configuration file. It has fields for baseUrl and token, along with their respective getter and setter methods.

### ConfigurationLoader Class
The ConfigurationLoader class is responsible for loading the configuration settings from the config.yaml file. It follows the Singleton pattern to ensure that only one instance of the configuration settings is loaded and used throughout the application. The class has the following methods:

- `getInstance()`: Returns the instance of ConfigurationLoader. If an instance doesn't already exist, it creates one and loads the configuration settings.
- `loadConfiguration()`: Private method that loads the configuration settings from the config.yaml file and sets the Configuration instance. It also validates the presence of the base_url and token properties in the file.
- `getConfiguration()`: Returns the Configuration instance.

## RestClient

RestClient is a utility class that simplifies making API requests using Rest Assured. It provides a set of static 
methods for sending HTTP requests, such as GET, POST, PUT, PATCH, and DELETE. The main goal of this class is to 
abstract away the Rest Assured usage details, making it easier to manage API calls and handle common tasks like 
authentication.

### Methods

RestClient provides the following static methods for sending HTTP requests:

- `get`(String url, Map<String, ?> queryParams, String token): Sends a GET request to the specified URL with optional query parameters and an optional Bearer token for authentication.
- `post`(String url, Map<String, ?> queryParams, Object body, String token): Sends a POST request to the specified URL with optional query parameters, a request body, and an optional Bearer token for authentication.
- `put`(String url, Map<String, ?> queryParams, Object body, String token): Sends a PUT request to the specified URL with optional query parameters, a request body, and an optional Bearer token for authentication.
- `patch`(String url, Map<String, ?> queryParams, Object body, String token): Sends a PATCH request to the specified URL with optional query parameters, a request body, and an optional Bearer token for authentication.
- `delete`(String url, Map<String, ?> queryParams, String token): Sends a DELETE request to the specified URL with optional query parameters and an optional Bearer token for authentication.

Notes

- The RestClient methods use Rest Assured to send HTTP requests and handle responses.

- The url parameter should include the base URL and the API endpoint.
- The queryParams parameter is a map of query parameters to be included in the request. If no query parameters are needed, pass null.
- The body parameter is used for POST, PUT, and PATCH requests and should contain the request payload (e.g., a JSON object). The payload can be a Java object, Map, or String.
- The token parameter is optional and should be used when authentication is required. If a token is provided, it will be included in the Authorization header as Bearer <token>. If no token is required, pass null.