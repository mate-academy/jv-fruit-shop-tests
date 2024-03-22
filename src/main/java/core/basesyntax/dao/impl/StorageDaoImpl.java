package core.basesyntax.dao.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.storage.Storage;
import java.util.Map;

public class StorageDaoImpl implements StorageDao {
    @Override
    public Map<String, Integer> getStorage() {
        return Storage.fruits;
    }

    @Override
    public void putProduct(String product, int quantity) {
        checkProduct(product);
        checkQuantity(quantity);
        Storage.fruits.put(product, quantity);
    }

    @Override
    public int getAmountByProductName(String product) {
        checkProduct(product);
        return Storage.fruits.get(product);
    }

    private void checkProduct(String product) {
        if (product == null || product.isEmpty()) {
            throw new IllegalArgumentException("Product is null or empty");
        }
    }

    private void checkQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity is less then 0");
        }
    }
}
