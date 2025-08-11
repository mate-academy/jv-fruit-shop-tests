package core.basesyntax.storage;

import java.util.HashMap;
import java.util.Map;

public class FruitShop {
    public static final Map<String, Integer> storage = new HashMap<>();

    public static int getQuantity(String fruit) {
        return storage.getOrDefault(fruit, 0);
    }
}
