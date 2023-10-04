package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StrategyProcessor;
import core.basesyntax.strategy.OperationBalance;
import core.basesyntax.strategy.OperationPurchase;
import core.basesyntax.strategy.OperationReturn;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationSupply;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageStrategyProcessor implements StrategyProcessor {

    private final Storage storage;
    private final Map<FruitTransaction.Operation, OperationStrategy> operationStrategyMap;

    public StorageStrategyProcessor(Storage storage) {
        this.storage = storage;
        operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(
                FruitTransaction.Operation.BALANCE, new OperationBalance(storage));
        operationStrategyMap.put(
                FruitTransaction.Operation.PURCHASE, new OperationPurchase(storage));
        operationStrategyMap.put(
                FruitTransaction.Operation.RETURN, new OperationReturn(storage));
        operationStrategyMap.put(
                FruitTransaction.Operation.SUPPLY, new OperationSupply(storage));
    }

    @Override
    public void processTransactionStrategies(List<FruitTransaction> transactionList) {
        for (FruitTransaction transaction : transactionList) {
            operationStrategyMap.get(transaction.getOperation()).handleOperation(transaction);
        }
    }
}
