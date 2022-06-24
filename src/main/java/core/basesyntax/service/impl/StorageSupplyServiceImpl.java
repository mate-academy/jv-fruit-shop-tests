package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.StorageSupplyService;

public class StorageSupplyServiceImpl implements StorageSupplyService {
    private Storage storage;

    public StorageSupplyServiceImpl() {
        this.storage = new Storage();
    }

    public StorageSupplyServiceImpl(Storage outerStorage) {
        this.storage = outerStorage;
    }

    @Override
    public void add(String fruit, Integer amount) {
        if (storage.getFruitsInStorage().containsKey(fruit)) {
            Integer newAmount = storage.getFruitsInStorage().get(fruit) + amount;
            storage.put(fruit, newAmount);
        } else {
            storage.put(fruit, amount);
        }
    }

    @Override
    public void subtract(String fruit, Integer amount) {
        if (storage.getFruitsInStorage().containsKey(fruit)) {
            storage.put(fruit, storage.getFruitsInStorage().get(fruit) - amount);
        } else {
            Integer minusAmount = amount * (-1);
            storage.put(fruit, minusAmount);
        }
    }
}
