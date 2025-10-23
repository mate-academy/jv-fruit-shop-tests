package core.basesyntax.db;

import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    public static final List<Fruit> fruits = new ArrayList<>();
    private static final Map<String, Integer> storage = new HashMap<>();

    private Storage() {
    }

    public static Map<String, Integer> getStorage() {
        return storage;
    }
}
