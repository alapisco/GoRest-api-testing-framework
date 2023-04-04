package gorest.api.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Utility class for reading JSON data from a file.
 */
public class JsonDataReader {

    /**
     * Reads JSON data from a file and returns a map representation of the data.
     *
     * @param fileName The file name of the JSON data file in the resources folder.
     * @return A map representation of the JSON data.
     * @throws IOException If there is an issue reading the JSON data file.
     */
    public static Map<String, Object> readJsonData(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = JsonDataReader.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IOException("File not found: " + fileName);
        }

        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {};
        return objectMapper.readValue(inputStream, typeReference);
    }
}
