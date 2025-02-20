package core.basesyntax.db;

import java.util.LinkedHashMap;
import java.util.Map;

public class Storage {
    private static Map<String, Integer> fruits = new LinkedHashMap<>();

    public static Map<String, Integer> getFruits() {
        return fruits;
    }

    public static void setFruits(Map<String, Integer> fruits) {
        if (fruits == null) {
            throw new IllegalArgumentException("Fruits map cannot be null");
        }
        Storage.fruits = fruits;
    }
}
