package strategy;

import model.FruitTransaction;

public interface OperationStrategy {
    TransactionHandler getStrategy(FruitTransaction.Operation operationType);
}
