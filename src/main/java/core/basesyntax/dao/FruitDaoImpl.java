package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitInStorage;
import java.util.List;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void add(FruitInStorage product) {
        Storage.FRUITS.put(product.getName(), product.getAmount());
    }

    @Override
    public FruitInStorage getByName(String fruit) {
        Integer amountOfFruit = Storage.FRUITS.get(fruit);
        return amountOfFruit == null ? null : new FruitInStorage(fruit, amountOfFruit);
    }

    @Override
    public void update(FruitInStorage product, int amount) {
        product.setAmount(amount);
        Storage.FRUITS.put(product.getName(), amount);
    }

    @Override
    public List<FruitInStorage> getAll() {
        return Storage.FRUITS.entrySet().stream()
                .map(e -> new FruitInStorage(e.getKey(), e.getValue()))
                .toList();
    }
}
