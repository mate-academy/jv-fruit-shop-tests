package core.basesyntax.dao.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import java.util.Map;
import java.util.Optional;

public class FruitDaoImpl implements FruitDao {
    private final Map<Fruit, Integer> fruitMap;

    public FruitDaoImpl(Map<Fruit, Integer> fruitMap) {
        this.fruitMap = fruitMap;
    }

    @Override
    public Optional<Integer> get(Fruit fruit) {
        if (fruitMap.containsKey(fruit)) {
            return Optional.of(fruitMap.get(fruit));
        }
        return Optional.empty();
    }

    @Override
    public void put(Fruit fruit, int amount) {
        fruitMap.put(fruit, amount);
    }
}
