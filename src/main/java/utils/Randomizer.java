package utils;

import java.nio.charset.Charset;
import java.util.Random;

public class Randomizer {

    public static String generateRandomString(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }
}
