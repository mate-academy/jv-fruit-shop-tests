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
        if (transactions == null || transactions.contains(null)) {
            throw new IllegalArgumentException("Transaction list cannot contain null elements");
        }
        for (FruitTransaction transaction : transactions) {
            if (transaction == null) {
                throw new IllegalArgumentException("Transaction cannot be null");
            }
            if (transaction.getOperation() == null) {
                throw new IllegalArgumentException("Transaction operation cannot be null");
            }
            if (transaction.getQuantity() < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative");
            }
            transactionProcessor.applyOperation(transaction);
        }
    }

    public Map<String, Integer> getInventory() {
        return Storage.inventory;
    }
}
