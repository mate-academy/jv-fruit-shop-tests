package basesyntax.strategy;

import basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void changeQuantity(FruitTransaction fruitTransaction);
}
