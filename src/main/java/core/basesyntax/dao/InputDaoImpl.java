package core.basesyntax.dao;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;

public class InputDaoImpl implements InputDao {

    @Override
    public void add(Fruit fruit) {
        FruitStorage.fruits.add(fruit);
    }

    @Override
    public Fruit getFruit(String fruitName) {
        return FruitStorage.fruits.stream()
                .filter(fruit -> fruit.getFruitName().equals(fruitName))
                .findFirst()
                .get();
    }
}
