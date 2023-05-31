package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    @Override
    public int add(String fruit, int quantity) {
        if (fruit == null) {
            throw new RuntimeException("The fruit is unspecified. Please specify the fruit.");
        }
        return Storage.fruitsQuantity
                .merge(fruit, quantity, Integer::sum);
    }

    @Override
    public int getQuantity(String fruit) {
        return Storage.fruitsQuantity
                .get(fruit);
    }

    @Override
    public Map<String, Integer> getAll() {
        return Storage.fruitsQuantity;
    }
}
