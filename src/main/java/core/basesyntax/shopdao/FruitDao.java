package core.basesyntax.shopdao;

import core.basesyntax.fruitsassortment.Fruit;
import java.util.Map;

public interface FruitDao {
    boolean add(Fruit fruit, int amount);

    int getAmountOfFruits(Fruit fruit);

    Map<Fruit, Integer> getAll();
}
