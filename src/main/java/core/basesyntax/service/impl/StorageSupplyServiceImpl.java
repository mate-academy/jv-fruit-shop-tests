package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.StorageSupplyService;
import java.util.Map;

public class StorageSupplyServiceImpl implements StorageSupplyService {
    private final Map<String, Integer> storage;

    public StorageSupplyServiceImpl() {
        this.storage = Storage.fruits;
    }

    public StorageSupplyServiceImpl(Map<String, Integer> outerStorage) {
        this.storage = outerStorage;
    }

    @Override
    public void add(String fruit, Integer amount) {
        if (storage.containsKey(fruit)) {
            Integer newAmount = storage.get(fruit) + amount;
            storage.put(fruit, newAmount);
        } else {
            storage.put(fruit, amount);
        }
    }

    @Override
    public void subtract(String fruit, Integer amount) {
        if (storage.containsKey(fruit)) {
            storage.put(fruit, storage.get(fruit) - amount);
        } else {
            Integer minusAmount = amount * (-1);
            storage.put(fruit, minusAmount);
        }
    }
}
