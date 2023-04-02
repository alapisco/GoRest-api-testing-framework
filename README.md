# GoRest Api Testing Framework

## Test Configuration 

In this testing framework, we use a configuration file to store the necessary settings for our API tests, 
such as the base URL and authentication token. 

## Configuration File
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


## Configuration Classes

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