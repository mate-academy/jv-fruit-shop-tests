package fruite.store.dao;

import fruite.store.db.Storage;

public class StorageDaoImpl implements StorageDao {
    @Override
    public void addFruitToStorage(String key, Integer value) {
        if (key == null || value == null) {
            throw new RuntimeException("Key or quantity can't be null!");
        }
        if (value <= 0) {
            throw new RuntimeException("Balance quantity"
                    + " can't be less than zero. Actual: " + value);
        }
        Storage.fruitStorage.put(key, value);
    }

    @Override
    public void addValueByKey(String key, Integer value) {
        if (key == null || value == null) {
            throw new RuntimeException("Key or quantity can't be null!");
        }
        Integer newValue = Storage.fruitStorage.get(key) + value;
        Storage.fruitStorage.put(key, newValue);
    }

    @Override
    public void subtractValueByKey(String key, Integer value) {
        if (key == null || value == null) {
            throw new RuntimeException("Key or quantity can't be null!");
        }
        Integer newValue = Storage.fruitStorage.get(key) - value;
        if (newValue < 0) {
            throw new RuntimeException("Fruit quantity can't be less than zero");
        }
        Storage.fruitStorage.put(key, newValue);
    }
}
