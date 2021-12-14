package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.shop.item.Fruit;
import java.util.List;

public class FruitDaoImpl implements FruitDao {

    @Override
    public void add(Fruit fruit) {
        Storage.fruits.add(new Fruit(fruit.getName(), fruit.getQuality()));
    }

    @Override
    public Fruit get(Fruit fruit) {
        try {
            return Storage.fruits.stream()
                    .filter(f -> f.getName().equals(fruit.getName()))
                    .findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void update(Fruit fruit) {
        Storage.fruits.set(Storage.fruits.indexOf(fruit),
                new Fruit(fruit.getName(),
                        fruit.getQuality()));
    }

    @Override
    public List<Fruit> getAll() {
        return Storage.fruits;
    }
}
