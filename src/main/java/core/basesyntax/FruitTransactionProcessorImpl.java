package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionProcessor;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationsHandler;
import java.util.List;

public record FruitTransactionProcessorImpl(
        OperationStrategy operationStrategy) implements
        FruitTransactionProcessor {

    public FruitTransactionProcessorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions) {
        transactions.forEach(transaction -> {
            OperationsHandler operationHandler = operationStrategy.getHandler(transaction);
            operationHandler.applyOperation(transaction);
        });
    }

}
