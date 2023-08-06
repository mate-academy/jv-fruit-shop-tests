package core.basesyntax.strategy.impl;

import core.basesyntax.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.transactionhandler.TransactionHandler;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    private final Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap;

    public TransactionStrategyImpl(Map<FruitTransaction.Operation,
            TransactionHandler> transactionHandlerMap) {
        this.transactionHandlerMap = transactionHandlerMap;
    }

    @Override
    public TransactionHandler get(FruitTransaction.Operation operationType) {
        if (operationType == null) {
            throw new FruitShopException("Operation type can't be null");
        }
        return transactionHandlerMap.get(operationType);
    }
}
