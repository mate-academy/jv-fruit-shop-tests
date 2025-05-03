package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private final OperationHandlerStrategy operationStrategy;

    public FruitServiceImpl(OperationHandlerStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void handleTransactions(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions == null || fruitTransactions.isEmpty()) {
            throw new RuntimeException("There is nothing to handle"
                    + " if fruitTransactions is null or empty");
        }
        for (FruitTransaction transaction : fruitTransactions) {
            checkIfTransactionDataValid(transaction);
            OperationHandler handler = operationStrategy.getHandler(transaction.getOperation());
            handler.apply(transaction);
        }
    }

    private void checkIfTransactionDataValid(FruitTransaction transaction) {
        if (transaction.getOperation() == null
                || transaction.getFruit() == null
                || transaction.getQuantity() < 0) {
            throw new RuntimeException("Wrong transaction data to handle " + System.lineSeparator()
                    + "operation: " + transaction.getOperation() + System.lineSeparator()
                    + "fruit name: " + transaction.getFruit() + System.lineSeparator()
                    + "quantity: " + transaction.getQuantity());
        }
    }
}
