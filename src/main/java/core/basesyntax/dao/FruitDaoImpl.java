package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void save(Fruit fruit) {
        Storage.fruitStorage.put(fruit.getName(), fruit.getQuantity());
    }

    @Override
    public Integer getValue(String fruitName) {
        return Storage.fruitStorage.get(fruitName);
    }

    @Override
    public List<Fruit> getAll() {
        List<Fruit> fruitList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : Storage.fruitStorage.entrySet()) {
            Fruit fruit = new Fruit();
            fruit.setName(entry.getKey());
            fruit.setQuantity(entry.getValue());
            fruitList.add(fruit);
        }
        return fruitList;
    }

}


