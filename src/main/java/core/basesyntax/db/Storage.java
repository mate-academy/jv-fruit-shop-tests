package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static Map<String, Integer> fruits = new HashMap<>();

    public static Map<String, Integer> getFruits() {
        return fruits;
    }

    public static void setFruits(Map<String, Integer> fruits) {
        Storage.fruits = fruits;
    }
}
