package core.fruitshop.dao;

import core.fruitshop.db.Storage;
import core.fruitshop.model.Fruit;
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
