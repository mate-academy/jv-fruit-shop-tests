package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Set;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void addFruit(Fruit fruit) {
        if (fruit == null || fruit.getTypeOfFruit() == null) {
            throw new RuntimeException("Fruit can't be null");
        }
        Storage.fruitsStorage.put(fruit, 0);
    }

    @Override
    public void setQuantity(Fruit fruit, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Passed quantity can't be <0. Params: quantity " + quantity);
        }
        if (fruit == null || fruit.getTypeOfFruit() == null) {
            throw new RuntimeException("Fruit can't be null");
        }
        if (Storage.fruitsStorage.containsKey(fruit)) {
            Storage.fruitsStorage.replace(fruit, quantity);
            return;
        }
        Storage.fruitsStorage.put(fruit, quantity);
    }

    @Override
    public Integer getQuantity(Fruit fruit) {
        if (Storage.fruitsStorage.containsKey(fruit)) {
            return Storage.fruitsStorage.get(fruit);
        }
        throw new RuntimeException("No such fruit in Database. Params: " + fruit.getTypeOfFruit());
    }

    @Override
    public Set<Fruit> getFruitsSet() {
        return Storage.fruitsStorage.keySet();
    }
}

