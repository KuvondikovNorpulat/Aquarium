package uz.kuvondikov.enums;

import java.util.Random;

public enum Gender {
    MALE, FEMALE;
    private static final Random RANDOM = new Random();

    public static Gender randomGender() {
        return RANDOM.nextBoolean() ? MALE : FEMALE;
    }
}
