package core.basesyntax.service.impl;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.impl.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.TransactionProcessor;
import java.util.Map;

public class FruitTransactionProcessor implements TransactionProcessor<FruitTransaction> {
    private Map<OperationType, OperationHandler<FruitTransaction>> strategyMap;

    public FruitTransactionProcessor(Map<OperationType,
            OperationHandler<FruitTransaction>> strategyMap) {
        this.strategyMap = strategyMap;
    }

    @Override
    public void process(FruitTransaction transaction) {
        validateTransaction(transaction);
        OperationHandler<FruitTransaction> operationHandler
                = strategyMap.get(transaction.getTransactionType());
        if (operationHandler == null) {
            throw new RuntimeException("Unknown operation type: "
                    + transaction.getTransactionType());
        }
        operationHandler.handle(transaction);
    }

    private static void validateTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException("Error: transaction is null!");
        }
        if (transaction.getTransactionType() == null) {
            throw new RuntimeException("Error: transaction type is null!");
        }
        if (transaction.getProductType() == null) {
            throw new RuntimeException("Error: product type is null!");
        }
        if (transaction.getTransactionValue() < 0) {
            throw new RuntimeException("Error: transaction value can not be less then zero!");
        }
    }
}
