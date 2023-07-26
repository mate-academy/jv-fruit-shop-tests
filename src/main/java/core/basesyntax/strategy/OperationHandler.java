package core.basesyntax.strategy;

import core.basesyntax.dto.FruitTransaction;

public interface OperationHandler {
    int apply(FruitTransaction fruitTransaction);
}
