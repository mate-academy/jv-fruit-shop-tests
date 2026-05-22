package core.basesyntax.db;

import java.util.LinkedHashMap;
import java.util.Map;

public class Storage {
    private Map<String, Integer> fruits = new LinkedHashMap<>();

    public Map<String, Integer> getFruits() {
        return fruits;
    }

    public Map<String, Integer> getAll() {
        return fruits;
    }
}
