package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {

    private final Map<Fruit, Integer> storage;

    public FruitDaoImpl(Map<Fruit, Integer> storage) {
        this.storage = storage;
    }

    @Override
    public void addNewFruit(Fruit fruit, int quantity) {
        storage.put(fruit, quantity);
    }

    @Override
    public Integer getQuantityByFruit(Fruit fruit) {
        return storage.get(fruit);
    }

    @Override
    public void subtractQuantityByFruit(Fruit fruit, int quantityToSubtract) {
        if (quantityToSubtract <= storage.get(fruit)) {
            storage.replace(fruit,
                    storage.get(fruit) - quantityToSubtract);
        } else {
            throw new RuntimeException("Quantity to subtract must be less than actual quantity of"
                    + " fruits. The actual quantity of " + fruit.getName() + " is: " + storage.get(fruit));
        }
    }

    @Override
    public void addQuantityByFruit(Fruit fruit, int quantityToAdd) {
        storage.replace(fruit,
                storage.get(fruit) + quantityToAdd);
    }

    @Override
    public Map<Fruit, Integer> getAll() {
        return storage;
    }
}
