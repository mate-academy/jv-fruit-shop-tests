package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitService;

public class FruitServiceImpl implements FruitService {
    private Storage storage;

    public FruitServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void save(String nameFruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity should be non-negative");
        }
        storage.addFruit(nameFruit, quantity);
    }
}
