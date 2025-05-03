package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Map;

public class FruitShopService {
    private final TransactionProcessor transactionProcessor;

    public FruitShopService(TransactionProcessor transactionProcessor) {
        this.transactionProcessor = transactionProcessor;
    }

    public void processTransactions(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new IllegalArgumentException("Transaction list cannot be null");
        }
        for (FruitTransaction transaction : transactions) {
            transactionProcessor.applyOperation(transaction);
        }
    }

    public Map<String, Integer> getInventory() {
        return Storage.inventory;
    }
}
