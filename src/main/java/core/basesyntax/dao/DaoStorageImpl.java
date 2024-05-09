package core.basesyntax.dao;

import core.basesyntax.db.StorageImpl;
import core.basesyntax.exception.DaoStorageException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DaoStorageImpl implements DaoStorage {
    private final Map<String, Integer> storage;

    public DaoStorageImpl() {
        storage = StorageImpl.getStorage();
        if (storage == null) {
            throw new DaoStorageException("The Storage is null");
        }
    }

    @Override
    public void setNewValue(String fruit, Integer quantity) {
        if (fruit == null || quantity == null || quantity < 0) {
            throw new DaoStorageException("The arguments fruit "
                    + "or/and quantity is NULL or negative");
        }
        storage.put(fruit, quantity);
    }

    @Override
    public void concatenateValue(String fruit, Integer quantity) {
        if (fruit == null || quantity == null || quantity < 0) {
            throw new DaoStorageException("The arguments fruit "
                    + "or/and quantity is NULL or negative");
        }
        storage.merge(fruit, quantity, Integer::sum);
    }

    @Override
    public int getValue(String fruit) {
        return storage.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getKey(), fruit))
                .findAny()
                .orElseThrow(() -> new DaoStorageException("The fruit is null or not found"))
                .getValue();
    }

    @Override
    public Set<Map.Entry<String, Integer>> getStatistic() {
        if (storage.entrySet().isEmpty()) {
            throw new DaoStorageException("The storage is empty.");
        }
        return storage.entrySet();
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
