package core.basesyntax.service;

import core.basesyntax.db.Storage;

public class InventoryService {

    public void addFruit(String fruit, int quantity) {
        Storage.inventory.put(fruit, Storage.inventory.getOrDefault(fruit, 0) + quantity);
    }

    public int getQuantity(String fruit) {
        return Storage.inventory.getOrDefault(fruit, 0);
    }

    public void removeFruit(String fruit, int quantity) {
        int currentQuantity = Storage.inventory.getOrDefault(fruit, 0);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough fruit to remove");
        }
        Storage.inventory.put(fruit, currentQuantity - quantity);
    }
}
