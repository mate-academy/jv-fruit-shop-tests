package core.basesyntax.db;

import java.util.Map;
import java.util.TreeMap;

public class Storage {
    private static Map<String, Integer> fruitStorage = new TreeMap<>();

    public static Map<String, Integer> getFruitStorage() {
        return fruitStorage;
    }

    public static void setFruitStorage(Map<String,Integer> fruitStorage) {
        Storage.fruitStorage = fruitStorage;
    }
}
