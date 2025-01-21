package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class FruitShopService {
    private final Map<OperationType, OperationHandler> operationStrategy;

    public FruitShopService(Map<OperationType, OperationHandler> operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public void processTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationType operation = transaction.getOperation();
            String fruit = transaction.getFruit();
            int quantity = transaction.getQuantity();
            TransactionProcessor.applyOperation(operation, fruit, quantity, operationStrategy);
        }
    }

    public Map<String, Integer> getInventory() {
        return Storage.inventory;
    }
}
