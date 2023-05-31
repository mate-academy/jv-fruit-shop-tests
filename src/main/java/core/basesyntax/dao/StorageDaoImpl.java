package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class StorageDaoImpl implements StorageDao {
    private static final int EMPTY_STOCK = 0;

    @Override
    public int add(Fruit fruit, int quantity) {
        return Optional.ofNullable(Storage.storage.put(fruit, quantity)).orElse(EMPTY_STOCK);
    }

    @Override
    public int update(Fruit fruit, int quantity) {
        int currentQuantity = get(fruit);
        return Optional.ofNullable(Storage.storage.put(fruit, currentQuantity + quantity))
                .orElse(EMPTY_STOCK);
    }

    @Override
    public int get(Fruit fruit) {
        return Storage.storage.getOrDefault(fruit, EMPTY_STOCK);
    }

    @Override
    public Set<Map.Entry<Fruit, Integer>> getAll() {
        return Storage.storage.entrySet();
    }
}
