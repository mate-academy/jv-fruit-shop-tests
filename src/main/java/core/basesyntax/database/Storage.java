package core.basesyntax.database;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static Map<Fruit, Integer> fruitDataBase = new HashMap<>();

    public static Map<Fruit, Integer> getFruitDataBase() {
        return fruitDataBase;
    }
}
