package core.basesyntax.dao;

import core.basesyntax.db.StorageFruits;
import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void add(Fruit fruit) {
        if (fruit == null) {
            throw new RuntimeException("Can't write wrong data to Storage");
        }
        StorageFruits.fruits.add(fruit);
    }

    @Override
    public Fruit get(String fruitName) {
        if (fruitName == null || fruitName.isEmpty()) {
            throw new RuntimeException("Can't get from Storage Fruit, "
                    + "fruitName == null");
        }
        return StorageFruits.fruits.stream()
                .filter(fruit -> fruit.getFruitName().equals(fruitName))
                .findFirst().get();
    }

    @Override
    public List<Fruit> getAll() {
        return new ArrayList<>(StorageFruits.fruits);
    }
}
