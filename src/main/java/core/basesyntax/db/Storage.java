package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static Map<String, Integer> storage = new HashMap<>();

    public static Integer getFruitAmount(String fruit) {
        if (storage.isEmpty() || !storage.containsKey(fruit)) {
            return 0;
        }
        return storage.get(fruit);
    }

    public static void putFruitToStorage(String fruit, Integer amount) {
        if (fruit == null) {
            throw new RuntimeException("Can't add null fruit to storage!");
        }
        storage.put(fruit, amount);
    }

    public static boolean containsFruit(String fruit) {
        return storage.containsKey(fruit);
    }

    public static void clearStorage() {
        storage.clear();
    }

    public static Map<String, Integer> getStorage() {
        return storage;
    }

}
