package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.Map;
import java.util.Objects;

public class TransactionProcessorImpl implements TransactionProcessor {
    private final Map<Transaction.Operation, OperationStrategy> handlers;

    public TransactionProcessorImpl(Map<Transaction.Operation, OperationStrategy> handlers) {
        Objects.requireNonNull(handlers);
        if (handlers.isEmpty()) {
            throw new IllegalArgumentException("Provided handlers is empty");
        }
        this.handlers = handlers;
    }

    @Override
    public void process(Transaction transaction) {
        handlers.get(transaction.getOperation())
                .apply(transaction.getFruit(), transaction.getQuantity());
    }
}
