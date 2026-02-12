package core.basesyntax.db;

import java.util.LinkedHashMap;
import java.util.Map;

public class Storage {
    private static Map<String, Integer> storedFruits = new LinkedHashMap<>();

    public static Map<String, Integer> getStoredFruits() {
        return storedFruits;
    }
}
