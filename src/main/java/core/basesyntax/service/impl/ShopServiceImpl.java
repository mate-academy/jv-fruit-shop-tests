package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public ShopServiceImpl(Map<FruitTransaction.Operation, OperationHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions,
                                    Map<String, Integer> inventory) {
        for (FruitTransaction transaction : transactions) {
            if (transaction.getQuantity() < 0) {
                throw new RuntimeException("Quantity cannot be negative");
            }
            if (transaction.getQuantity() == 0) {
                inventory.putIfAbsent(transaction.getFruit(), 0);
                continue;
            }
            OperationHandler handler = operationHandlers.get(transaction.getOperation());
            if (handler == null) {
                throw new RuntimeException("Invalid operation: "
                        + transaction.getOperation());
            }
            handler.handle(transaction, inventory);
        }
    }
}
