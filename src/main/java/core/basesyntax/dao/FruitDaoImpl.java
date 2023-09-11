package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitInStorage;
import java.util.Collection;

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
    public Collection<FruitInStorage> getAll() {
        return Storage.fruits().values();
    }
}
