package core.basesyntax.strategy.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.transaction.TransactionHandler;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    private Map<Transaction, TransactionHandler> transactionHandlerMap;

    public TransactionStrategyImpl(Map<Transaction, TransactionHandler> transactionHandlerMap) {
        this.transactionHandlerMap = transactionHandlerMap;
    }

    @Override
    public TransactionHandler get(Transaction transaction) {
        return transactionHandlerMap.get(transaction);
    }
}
