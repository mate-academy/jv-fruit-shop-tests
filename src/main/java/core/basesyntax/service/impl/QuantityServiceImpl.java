package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.QuantityService;
import java.util.Map;
import java.util.NoSuchElementException;

public class QuantityServiceImpl implements QuantityService {
    private static final Map<Fruit, Integer> STORAGE = Storage.fruits;

    @Override
    public void add(Fruit fruit, Integer quantity) {
        if (fruit == null) {
            throw new RuntimeException("Can't add fruit, its empty.");
        }
        if (quantity == null) {
            throw new RuntimeException("Can't add quantity, its empty.");
        }
        Integer variable = STORAGE.containsKey(fruit)
                ? STORAGE.replace(fruit, STORAGE.get(fruit) + quantity) :
                STORAGE.put(fruit, quantity);
    }

    @Override
    public void subtract(Fruit fruit, Integer quantity) {
        if (quantity == null) {
            throw new RuntimeException("Can't subtract quantity, its empty.");
        }
        if (STORAGE.containsKey(fruit)) {
            STORAGE.replace(fruit, STORAGE.get(fruit) - quantity);
        } else {
            throw new NoSuchElementException("There is no such fruit " + fruit + " at the storage");
        }
    }
}
