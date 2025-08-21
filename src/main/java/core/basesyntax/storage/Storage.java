package core.basesyntax.storage;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<String, Integer> storage = new HashMap<>();

    public static void put(String fruit, Integer quantity) {
        storage.put(fruit, quantity);
    }

    public static Integer get(String fruit) {
        return storage.get(fruit);
    }

    public static Map<String, Integer> getAll() {
        return storage;
    }
}
