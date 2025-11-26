package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class FruitsDaoImpl implements FruitsDao {

    @Override
    public void add(FruitTransaction fruits) {
        Storage.fruitsStorage.add(fruits);
    }

    @Override
    public FruitTransaction get(FruitTransaction fruits) {
        return Storage.fruitsStorage.stream()
                .filter(f -> f.getName().equals(fruits.getName()))
                .findFirst().orElseThrow(() -> new RuntimeException(
                        "Can't find fruit " + fruits.getName()));
    }

    @Override
    public List<FruitTransaction> getAll() {
        return Storage.fruitsStorage;
    }

    @Override
    public void set(FruitTransaction fruits) {
        get(fruits).setQuantity(fruits.getQuantity());
    }
}
