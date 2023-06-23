package core.basesyntax.fruit.shop.service.impl;

import core.basesyntax.fruit.shop.model.FruitTransaction;
import core.basesyntax.fruit.shop.service.TransactionHandler;
import core.basesyntax.fruit.shop.service.strategy.OperationHandler;
import core.basesyntax.fruit.shop.service.strategy.OperationStrategy;
import java.util.List;

public class TransactionHandlerImpl implements TransactionHandler {
    private OperationStrategy strategy;

    public TransactionHandlerImpl(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void parseStorage(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = strategy.getOperationHandler(transaction.getOperation());
            handler.handleTransaction(transaction);
        }
    }
}
