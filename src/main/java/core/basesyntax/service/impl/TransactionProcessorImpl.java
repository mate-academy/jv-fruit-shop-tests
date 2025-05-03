package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.HandlerStrategy;
import core.basesyntax.strategy.OperationHandler;

public class TransactionProcessorImpl implements TransactionProcessor {
    private static final String NULL_TRANSACTION_MESSAGE = "Can't process, transaction is null";
    private final HandlerStrategy handlerStrategy;

    public TransactionProcessorImpl(HandlerStrategy handlerStrategy) {
        this.handlerStrategy = handlerStrategy;
    }

    @Override
    public void processTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException(NULL_TRANSACTION_MESSAGE);
        }
        OperationHandler newHandler = handlerStrategy.getHandlerByOperationType(
                transaction.getOperationType());
        String fruitName = transaction.getFruitName();
        int amount = transaction.getQuantity();
        newHandler.executeTransaction(fruitName, amount);
    }
}
