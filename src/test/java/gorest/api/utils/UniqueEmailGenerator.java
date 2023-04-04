package gorest.api.utils;

import java.time.Instant;
import java.util.Random;

/**
 * Utility class for generating unique email addresses.
 */
public class UniqueEmailGenerator {

    /**
     * Generates a unique email address.
     *
     * @return A unique email address.
     */
    public static String generateUniqueEmail() {
        long currentTime = Instant.now().toEpochMilli();
        int randomInt = new Random().nextInt(10000);
        return String.format("%d-%d@email.com", currentTime, randomInt);
    }
}