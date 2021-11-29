package core.basesyntax.db;

import core.basesyntax.model.Fruit;
import java.util.LinkedHashMap;
import java.util.Map;

public class Storage {
    private static Map<Fruit, Integer> fruitsStore = new LinkedHashMap<>();

    public static Map<Fruit, Integer> getStore() {
        return fruitsStore;
    }

}
