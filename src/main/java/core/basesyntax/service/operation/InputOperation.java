package core.basesyntax.service.operation;

import core.basesyntax.model.FruitTransaction;

public interface InputOperation {
    void process(FruitTransaction fruitOperation);

    default void validate(FruitTransaction fruitOperation) {
        if (fruitOperation == null) {
            throw new RuntimeException("Fruit operation must not be null.");
        }
    }
}
