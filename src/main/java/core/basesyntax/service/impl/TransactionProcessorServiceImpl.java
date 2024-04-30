package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessorService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;

public class TransactionProcessorServiceImpl implements TransactionProcessorService {
    private Map<String, Integer> fruitCounts;
    private final Map<FruitTransaction.Operation, OperationStrategy> strategies;

    public TransactionProcessorServiceImpl(Map<FruitTransaction.Operation,
            OperationStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public Map<String, Integer> processTransactions(List<FruitTransaction> transactions) {
        fruitCounts = Storage.getFruits();
        if (transactions != null) {
            for (FruitTransaction transaction : transactions) {
                processSingleTransaction(transaction);
            }
        }
        return fruitCounts;
    }

    public void setFruitCounts(Map<String, Integer> fruitCounts) {
        this.fruitCounts = fruitCounts;
    }

    public void processSingleTransaction(FruitTransaction transaction) {
        FruitTransaction.Operation typeOfOperation = transaction.getType();
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();

        OperationStrategy strategy = strategies.get(typeOfOperation);
        if (strategy != null) {
            strategy.apply(fruitCounts, fruit, quantity);
        } else {
            throw new RuntimeException("Unknown operation type: " + typeOfOperation.getCode());
        }
    }
}
