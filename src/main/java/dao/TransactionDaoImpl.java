package dao;

import db.Storage;
import java.util.Map;
import model.FruitTransaction;
import strategy.*;

public class TransactionDaoImpl implements TransactionsDao {

    private final OperationStrategy handler;

    public TransactionDaoImpl(OperationStrategy handler) {
        this.handler = handler;
    }

    @Override
    public Map<String, Integer> getAll() {
        return Storage.fruitsStore;
    }

    @Override
    public void processTransaction(FruitTransaction transaction) {
        Integer currentQuantity = Storage.fruitsStore.getOrDefault(transaction.getFruit(), 0);
        TransactionHandler operation = handler.getStrategy(transaction.getOperation());

        if (operation == null) {
            throw new IllegalArgumentException("Unknown operation: " + transaction.getOperation());
        }

        Integer updatedQuantity = operation.apply(currentQuantity, transaction);
        Storage.fruitsStore.put(transaction.getFruit(), updatedQuantity);
    }

    public Integer getTransactionByName(String fruitName) {
        return Storage.fruitsStore.get(fruitName);
    }

    public void clearTransactions() {
        Storage.fruitsStore.clear();
    }
}
