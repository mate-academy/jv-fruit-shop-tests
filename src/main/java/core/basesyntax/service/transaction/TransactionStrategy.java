package core.basesyntax.service.transaction;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class TransactionStrategy {
    private Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap;

    public TransactionStrategy(Map<FruitTransaction.Operation,
            TransactionHandler> transactionHandlerMap) {
        if (transactionHandlerMap == null) {
            throw new RuntimeException("TransactionHandlerMap is null");
        }
        this.transactionHandlerMap = transactionHandlerMap;
    }

    public TransactionHandler get(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new RuntimeException("FruitTransaction operation is null");
        }
        return transactionHandlerMap.get(operation);
    }
}
