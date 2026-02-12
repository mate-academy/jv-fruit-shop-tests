package core.basesyntax.dao.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.models.InvalidDataException;

public class StorageDaoImpl implements StorageDao {
    private static final int ZERO_QUANTITY = 0;

    @Override
    public int getStoredQuantity(String fruitName) {
        if (fruitName == null) {
            throw new InvalidDataException("Null value is not allowed.");
        }
        if (fruitName.isEmpty()) {
            throw new InvalidDataException("Empty string argument is not allowed");
        }
        if (Storage.getStoredFruits().get(fruitName) == null) {
            return ZERO_QUANTITY;
        }
        return Storage.getStoredFruits().get(fruitName);
    }

    @Override
    public void addFruit(String fruitName, Integer quantity) {
        if (fruitName == null) {
            throw new InvalidDataException("Null value is not allowed");
        }
        Storage.getStoredFruits().put(fruitName, quantity);
    }

}
