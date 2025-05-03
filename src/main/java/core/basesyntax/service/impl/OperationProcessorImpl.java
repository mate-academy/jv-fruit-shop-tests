package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.HandlerStrategy;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;

public class OperationProcessorImpl implements TransactionProcessor {
    private final HandlerStrategy strategy;

    public OperationProcessorImpl(HandlerStrategy strategy) {
        this.strategy = strategy;
    }

    public void process(List<FruitTransaction> list) {
        checkInputListValidity(list);
        for (FruitTransaction transaction : list) {
            processSingleTransaction(transaction);
        }
    }

    private void checkInputListValidity(List<FruitTransaction> list) {
        if (list == null || list.isEmpty()) {
            throw new InvalidInputDataException("The list is null or empty");
        }
    }

    private void processSingleTransaction(FruitTransaction transaction) {
        OperationHandler handler = strategy.getHandlerByOperation(transaction.getOperationType());
        String productType = transaction.getProductName();
        int amount = transaction.getAmount();
        handler.handle(productType, amount);
    }
}
