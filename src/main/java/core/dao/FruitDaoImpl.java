package core.dao;

import core.db.Storage;
import core.model.Fruit;
import java.util.List;

public class FruitDaoImpl implements FruitDao {
    @Override
    public Fruit add(Fruit fruit) {
        Storage.fruits.add(fruit);
        return fruit;
    }

    @Override
    public Fruit get(String name) {
        return Storage.fruits.stream()
                .filter(f -> f.getName().equals(name))
                .findFirst()
                .get();
    }

    @Override
    public List<Fruit> getAll() {
        return Storage.fruits;
    }
}
