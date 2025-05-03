package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.transaction.TransactionHandler;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    private final Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap;

    public TransactionStrategyImpl(Map<FruitTransaction.Operation,
            TransactionHandler> transactionHandlerMap) {
        this.transactionHandlerMap = transactionHandlerMap;
    }

    @Override
    public TransactionHandler get(FruitTransaction.Operation operation) {
        handleErrors(operation);
        return transactionHandlerMap.get(operation);
    }

    private void handleErrors(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new RuntimeException("Operation is NULL");
        }
        if (transactionHandlerMap.get(operation) == null) {
            throw new RuntimeException("Operation is not Exist");
        }
    }
}
