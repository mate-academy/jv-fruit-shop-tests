package core.basesyntax.service.strategy;

import core.basesyntax.storage.Storage;

public class ReturnOperation extends AddOperation {
    @Override
    public void apply(String fruit, Integer quantity) {
        if (!Storage.fruits.containsKey(fruit)) {
            throw new IllegalArgumentException("Fruit not found: " + fruit);
        }
        super.apply(fruit, quantity);
    }

}
