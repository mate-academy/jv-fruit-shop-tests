package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final Map<String, Integer> FRUIT_BALANCE = new HashMap<>();

    static {
        FRUIT_BALANCE.put("apple", 0);
        FRUIT_BALANCE.put("banana", 0);
    }

    public Storage() {
    }

    public static void addFruits(String fruit, Integer number) {
        FRUIT_BALANCE.put(fruit, number);
    }

    public static void clear() {
        FRUIT_BALANCE.clear();
    }

    public static void removeFruit(String fruit, int quantity) {
        int currentQuantity = FRUIT_BALANCE.getOrDefault(fruit, 0);
        if (currentQuantity > 0) {
            int newQuantity = Math.max(currentQuantity - quantity, 0);
            FRUIT_BALANCE.put(fruit, newQuantity);
        }
    }

    public static int getFruitBalance(String fruit) {

        return FRUIT_BALANCE.getOrDefault(fruit, 0);
    }

    public static Map<String, Integer> getAllFruitBalances() {
        return new HashMap<>(FRUIT_BALANCE);
    }

    @Override
    public String toString() {
        return "Storage{" + FRUIT_BALANCE + "}";
    }
}
