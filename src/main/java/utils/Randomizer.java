package utils;

import java.util.Random;

public class Randomizer {

    /**
     * Generate new string
     * https://www.baeldung.com/java-random-string
     * @param length
     * @return
     */
    public static String generateRandomString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 57; // numeral '9'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
