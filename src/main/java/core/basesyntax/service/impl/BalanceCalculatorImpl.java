package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;

public class BalanceCalculatorImpl implements Operation {
    private final OperationStrategy strategy;
    private BalanceChecker balanceChecker = new BalanceChecker();

    public BalanceCalculatorImpl(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Map<String, Integer> update(List<FruitTransaction> data) {
        for (FruitTransaction fruit : data) {
            String fruitName = fruit.getFruit();
            int quantity = fruit.getQuantity();
            OperationHandler operationService = strategy.getOperationService(fruit.getOperation());
            int updatedBalance = operationService
                    .operate(quantity, Storage.fruitsStorage.getOrDefault(fruitName, 0));
            Storage.fruitsStorage.put(fruitName, updatedBalance);
        }
        for (Map.Entry<String, Integer> entry : Storage.fruitsStorage.entrySet()) {
            balanceChecker.checkBalance(entry.getValue(), entry.getKey());
        }
        return Storage.fruitsStorage;
    }
}
