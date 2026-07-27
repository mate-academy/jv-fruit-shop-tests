package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static Map<String, Integer> fruits = new HashMap<>();

    private Storage() {
    }

    public static Map<String, Integer> getFruits() {
        return fruits;
    }
}
