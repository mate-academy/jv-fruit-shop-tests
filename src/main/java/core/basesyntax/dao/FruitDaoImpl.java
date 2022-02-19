package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;

public class FruitDaoImpl implements FruitDao {
    @Override
    public boolean add(Fruit fruit) {
        if (fruit != null) {
            Storage.fruits.add(fruit);
            return true;
        }
            throw new RuntimeException("Fruit can`t be null");
    }

    @Override
    public Fruit get(String fruitType) {
        if (!fruitType.isEmpty()) {
            return Storage.fruits.stream()
                    .filter(f -> f.getFruitType().equals(fruitType))
                    .findFirst().get();
        }
            throw new RuntimeException("FruitType can`t be empty");
    }

    @Override
    public boolean update(Fruit fruit) {
        if (fruit == null) {
            throw new RuntimeException("Fruit can`t be null");
        } else if (fruit.getFruitType().isEmpty()) {
            throw new RuntimeException("FruitType can`t be empty");
        } else if (fruit.getAmount() <= 0) {
            throw new RuntimeException("FruitAmount can't be <= 0");
        }
        Fruit fruitFromStorage = get(fruit.getFruitType());
        fruitFromStorage.setAmount(fruit.getAmount());
        return true;
    }

    @Override
    public List<Fruit> getAll() {
        return new ArrayList<>(Storage.fruits);
    }
}
