package core.basesyntax.db;

import core.basesyntax.model.FruitInStorage;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static Map<String, FruitInStorage> fruits = new HashMap<>();

    public static Map<String, FruitInStorage> fruits() {
        return fruits;
    }

    public static void reset() {
        fruits = new HashMap<>();
    }
}
