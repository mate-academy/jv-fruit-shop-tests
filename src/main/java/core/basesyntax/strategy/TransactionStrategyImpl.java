package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.transaction.TransactionHandler;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    private Map<Operation, TransactionHandler> handlerMap;

    public TransactionStrategyImpl(Map<Operation, TransactionHandler> transactionHandlerMap) {
        this.handlerMap = transactionHandlerMap;
    }

    @Override
    public void handleOperation(Transaction transaction) {
        handlerMap.get(transaction.getOperation()).handle(transaction);
    }
}
