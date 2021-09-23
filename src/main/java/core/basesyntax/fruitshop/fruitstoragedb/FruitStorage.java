package core.basesyntax.fruitshop.fruitstoragedb;

import core.basesyntax.fruitshop.model.Fruit;

import java.util.HashMap;
import java.util.Map;

public class FruitStorage {
    private static final Map<Fruit, Integer> storage = new HashMap<Fruit,Integer>();

    public static Map<Fruit, Integer> getStorage() {
        return storage;
    }
}
