package core.basesyntax.storage;

import java.util.LinkedHashMap;
import java.util.Map;

public class Storage {
    private static final Map<String, Integer> fruits = new LinkedHashMap<>();

    public static Map<String, Integer> getFruits() {
        return fruits;
    }
}
