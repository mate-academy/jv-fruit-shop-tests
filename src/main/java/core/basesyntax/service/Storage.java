package core.basesyntax.service;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<String, Integer> inventory = new HashMap<>();

    public static void updateInventory(String fruit, int quantity) {
        inventory.put(fruit, quantity);
    }
}
