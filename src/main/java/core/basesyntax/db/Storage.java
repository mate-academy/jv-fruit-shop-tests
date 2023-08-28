package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<String,Integer> fruit = new HashMap<>();

    public static Map<String, Integer> getFruit() {
        return fruit;
    }

    public static int addFruits(String fruit, int quantity) {
        Storage.fruit.put(fruit, quantity);
        return quantity;
    }

    public static int getInformation(String typeFruit, int amount) {
        return fruit.getOrDefault(typeFruit, amount);
    }
}
