package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import java.util.stream.Collectors;

public class StorageDaoImpl implements StorageDao {
    @Override
    public void update(String fruitName, Integer amount) {
        if (fruitName == null) {
            throw new RuntimeException("Fruit name cannot be null.");
        } else if (amount == null) {
            throw new RuntimeException("Operation value cannot be null");
        } else if (amount < 0) {
            throw new RuntimeException("Operation value cannot be less than zero");
        } else {
            Storage.fruitsStorage.put(fruitName, amount);
        }
    }

    @Override
    public Integer getAmount(String fruitName) {
        if (Storage.fruitsStorage.containsKey(fruitName)) {
            return Storage.fruitsStorage.get(fruitName);
        }
        throw new RuntimeException("There's no such a fruit in a Storage: " + fruitName);
    }

    @Override
    public List<Fruit> getAll() {
        return Storage.fruitsStorage.entrySet().stream()
                .map(i -> new Fruit(i.getKey(),i.getValue()))
                .collect(Collectors.toList());
    }
}
