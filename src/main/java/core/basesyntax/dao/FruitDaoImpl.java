package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;
import java.util.Optional;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void update(String fruitName, int quantity) {
        Storage.fruitsMap.put(fruitName,quantity);
    }

    @Override
    public Optional<Integer> get(String fruitName) {
        return Optional.ofNullable(Storage.fruitsMap.get(fruitName));
    }

    @Override
    public Map<String, Integer> getAll() {
        return Storage.fruitsMap;
    }
}
