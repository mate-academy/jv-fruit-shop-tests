package core.basesyntax.storagedao;

import core.basesyntax.storage.Storage;

public class StorageDaoImpl implements StorageDao {

    @Override
    public void add(String fruit, int quantity) {
        if (quantity > 0) {
            Storage.storage.put(fruit, Storage.storage.getOrDefault(fruit, 0) + quantity);
            return;
        }
        throw new RuntimeException("Cannot add to storage because the quantity is "
                + quantity + ". Quantity must be greater than 0.");
    }

    @Override
    public void subtract(String fruit, int quantity) {
        if (Storage.storage.containsKey(fruit) && quantity <= Storage.storage.get(fruit)) {
            Storage.storage.merge(fruit, -quantity, Integer::sum);
        }
    }

    @Override
    public int getFruitQuantity(String fruit) {
        return Storage.storage.getOrDefault(fruit, 0);
    }

    @Override
    public void clear() {
        Storage.storage.clear();
    }
}
