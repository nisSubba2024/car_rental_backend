package org.example.carrentalbackend.utility;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomIdGenerator {
    private static final int DEFAULT_LENGTH = 8;
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Random random = new Random();

    public String generateId() {
        return generateId(DEFAULT_LENGTH);
    }

    public String generateId(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return stringBuilder.toString();
    }

}
