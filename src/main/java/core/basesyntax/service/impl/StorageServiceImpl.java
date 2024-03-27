package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidFruitException;
import core.basesyntax.service.StorageService;
import java.util.Map;

public class StorageServiceImpl implements StorageService {
    private final Storage storage;

    public StorageServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public int getQuantity(String key) {
        if (storage.getFruits().containsKey(key)) {
            return storage.getFruits().get(key);
        }
        throw new InvalidFruitException("We don't have that fruit " + key + " in the storage");
    }

    @Override
    public void add(String fruitName, Integer value) {
        storage.getFruits().put(fruitName, value);
    }

    @Override
    public Map<String, Integer> getAll() {
        return storage.getFruits();
    }

    @Override
    public void updateQuantity(String fruitName, Integer value) {
        if (storage.getFruits().containsKey(fruitName)) {
            storage.getFruits().put(fruitName, value);
        } else {
            add(fruitName, value);
        }
    }
}
