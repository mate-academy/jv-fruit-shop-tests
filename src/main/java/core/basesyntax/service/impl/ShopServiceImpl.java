package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public ShopServiceImpl(Map<FruitTransaction.Operation, OperationHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("Empty list of transactions!");
        }
        for (FruitTransaction transaction : transactions) {
            FruitTransaction.Operation currentOperation = transaction.getOperation();
            OperationHandler handler = operationHandlers.get(currentOperation);
            if (handler == null) {
                throw new IllegalArgumentException("Unsupported operation: " + currentOperation);
            }
            handler.doOperation(transaction);
        }
    }
}
