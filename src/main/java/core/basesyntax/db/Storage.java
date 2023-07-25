package core.basesyntax.db;

import core.basesyntax.service.impl.ErrorDataException;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    private Map<String, Integer> inventory;

    public Storage() {
        inventory = new HashMap<>();
    }

    public void updateFruitQuantity(String fruit, int quantity) throws ErrorDataException {
        if (quantity < 0) {
            throw new ErrorDataException("Input data error. Quantity coudn't be less then 0");
        }
        inventory.put(fruit, quantity);
    }

    public int getFruitQuantity(String fruit) {
        return inventory.getOrDefault(fruit, 0);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}
