package core.basesyntax.fruit.shop.service.strategy;

import fruit.shop.model.FruitTransaction;

public interface OperationStrategy {
    OperationHandler getOperationHandler(FruitTransaction.Operation operation);
}
