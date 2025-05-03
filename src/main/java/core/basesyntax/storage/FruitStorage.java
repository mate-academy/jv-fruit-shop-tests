package core.basesyntax.storage;

import java.util.HashMap;
import java.util.Map;

public class FruitStorage implements Storage {
    public static final Map<String, Integer> fruitStorage = new HashMap<>();

    private static void isNegative(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Fruit quantity should be more than 0");
        }
    }

    @Override
    public void saveItem(String fruit, int quantity) {
        isNegative(quantity);
        fruitStorage.put(fruit, quantity);
    }

    @Override
    public void supplyItem(String fruit, int quantity) {
        isNegative(quantity);
        fruitStorage.merge(fruit, quantity, Integer::sum);
    }

    @Override
    public void purchaseItem(String fruit, int quantity) {
        isNegative(quantity);
        if (!fruitStorage.containsKey(fruit) || fruitStorage.get(fruit) < quantity) {
            throw new IllegalArgumentException("Not enough fruit: " + fruit);
        }
        fruitStorage.merge(fruit, -quantity, Integer::sum);
    }

    @Override
    public int getAmount(String item) {
        return fruitStorage.get(item) == null ? 0 : fruitStorage.get(item);
    }

    @Override
    public void clear() {
        fruitStorage.clear();
    }
}
