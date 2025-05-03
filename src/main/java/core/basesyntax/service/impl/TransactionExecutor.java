package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.List;
import java.util.Map;

public class TransactionExecutor {
    private final OperationStrategyImpl strategy;

    public TransactionExecutor(Map<FruitTransaction.Operation, OperationHandler> strategyMap) {
        this.strategy = new OperationStrategyImpl(strategyMap);
    }

    public void execute(List<FruitTransaction> transactions) {
        if (transactions.isEmpty()) {
            throw new InvalidDataException("Invalid data");
        }
        for (FruitTransaction transaction : transactions) {
            strategy.get(transaction.getOperation())
                    .handle(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
