package core.basesyntax.dao;

import core.basesyntax.db.Storage;

public class StorageDaoImpl implements StorageDao {
    public static final String EXCEPTION_MESSAGE = "Invalid values";

    @Override
    public void add(String fruit, int quantity) {
        if (fruit == null) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }
        Storage.FRUITS.put(fruit, quantity);
    }

    @Override
    public int getQuantity(String fruit) {
        return Storage.FRUITS.get(fruit);
    }

}
