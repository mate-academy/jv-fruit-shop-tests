package core.basesyntax.db;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final Map<Fruit, Integer> storage = new HashMap<>();

    public static void put(Fruit fruit, Integer quantity) {
        storage.put(fruit, quantity);
    }

    public static Integer get(Fruit fruit) {
        return storage.get(fruit);
    }

    public static Map<Fruit, Integer> getAll() {
        return storage;
    }
}
