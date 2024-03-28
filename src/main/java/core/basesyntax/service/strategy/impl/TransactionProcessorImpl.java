package core.basesyntax.service.strategy.impl;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.TransactionProcessor;
import exception.CustomException;
import java.util.List;

public class TransactionProcessorImpl implements TransactionProcessor {
    private OperationStrategy operationStrategy;

    public TransactionProcessorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public void process(List<FruitTransactionDto> transactions) {
        if (transactions == null) {
            throw new CustomException("Transaction list is null");
        }

        for (var transaction : transactions) {
            if (transaction == null) {
                throw new CustomException("Transaction object is null");
            }
            OperationHandler handler = operationStrategy.get(transaction);
            handler.apply(transaction);
        }
    }
}
