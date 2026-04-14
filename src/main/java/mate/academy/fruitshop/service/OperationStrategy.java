package mate.academy.fruitshop.service;

import mate.academy.fruitshop.model.FruitTransaction;

public interface OperationStrategy {
    OperationHandler get(FruitTransaction.Operation operation);
}
