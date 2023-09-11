package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitInStorage;
import java.util.ArrayList;
import java.util.List;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void add(FruitInStorage product) {
        Storage.fruits().put(product.getName(), product);
    }

    @Override
    public FruitInStorage getByName(String name) {
        return Storage.fruits().get(name);
    }

    @Override
    public void update(FruitInStorage product, int amount) {
        product.setAmount(amount);
        Storage.fruits().put(product.getName(), product);
    }

    @Override
    public List<FruitInStorage> getAll() {
        return new ArrayList<>(Storage.fruits().values());
    }
}
