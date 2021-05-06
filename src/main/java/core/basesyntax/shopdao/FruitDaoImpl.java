package core.basesyntax.shopdao;

import core.basesyntax.dbimitation.Storage;
import core.basesyntax.fruitsassortment.Fruit;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {

    @Override
    public boolean add(Fruit fruit, int amount) {
        Storage.getFruits().put(fruit, amount);
        return true;
    }

    @Override
    public int getAmountOfFruits(Fruit fruit) {
        return Storage.getFruits().getOrDefault(fruit, 0);
    }

    @Override
    public Map<Fruit, Integer> getAll() {
        return Storage.getFruits();
    }
}
