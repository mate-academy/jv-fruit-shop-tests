package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<String,Integer> FRUIT_MAPS = new HashMap<>();

    public static Map<String, Integer> getFruit() {
        return FRUIT_MAPS;
    }

    public static int addFruits(String fruit, int quantity) {
        Storage.FRUIT_MAPS.put(fruit, quantity);
        return quantity;
    }

    public static int getInformation(String typeFruit, int amount) {
        return FRUIT_MAPS.getOrDefault(typeFruit, amount);
    }
}
