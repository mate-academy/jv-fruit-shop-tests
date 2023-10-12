package core.basesyntax.db;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static Storage instance;
    private final Map<String, Integer> fruitInventory;

    private Storage() {
        this.fruitInventory = new HashMap<>();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public Map<String, Integer> getFruitInventory() {
        return fruitInventory;
    }

    public void updateFruitQuantity(FruitTransaction.Operation operation,
                                    String fruitName, int quantity) {
        int currentQuantity = fruitInventory.getOrDefault(fruitName, 0);
        switch (operation) {
            case BALANCE:
            case SUPPLY:
            case RETURN:
                currentQuantity += quantity;
                break;
            case PURCHASE:
                currentQuantity -= quantity;
                break;
            default:
                throw new IllegalArgumentException("Unknown operation type: "
                        + operation);
        }
        fruitInventory.put(fruitName, currentQuantity);
    }
}
