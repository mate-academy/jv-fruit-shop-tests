package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {

    @Override
    public void getOperationAndProcess(List<FruitTransaction> transactions,
                                                   Map<FruitTransaction.Operation,
                                                           OperationHandler> operationHashMap) {
        if (transactions.isEmpty() || operationHashMap.isEmpty()) {
            throw new RuntimeException("Incorrect input parameters");
        }

        for (FruitTransaction transaction : transactions) {
            OperationHandler operation = operationHashMap.get(transaction.getOperation());
            if (operation != null) {
                operation.processWithTransaction(transaction);
            }
        }
    }
}
