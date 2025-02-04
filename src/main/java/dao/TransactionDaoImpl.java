package dao;

import db.Storage;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import strategy.BalanceHandler;
import strategy.PurchaseHandler;
import strategy.ReturnHandler;
import strategy.SupplyHandler;
import strategy.TransactionHandler;

public class TransactionDaoImpl implements TransactionsDao {

    private final Map<FruitTransaction.Operation, TransactionHandler> operationHandlers
            = new HashMap<>();

    public TransactionDaoImpl() {
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
    }

    @Override
    public Map<String, Integer> getAll() {
        return Storage.fruitsStore;
    }

    @Override
    public void processTransaction(FruitTransaction transaction) {
        Integer currentQuantity = Storage.fruitsStore.getOrDefault(transaction.getFruit(), 0);
        TransactionHandler handler = operationHandlers.get(transaction.getOperation());

        if (handler == null) {
            throw new IllegalArgumentException("Unknown operation: " + transaction.getOperation());
        }

        Integer updatedQuantity = handler.apply(currentQuantity, transaction);
        Storage.fruitsStore.put(transaction.getFruit(), updatedQuantity);
    }

    public Integer getTransactionByName(String fruitName) {
        return Storage.fruitsStore.get(fruitName);
    }

    public void clearTransactions() {
        Storage.fruitsStore.clear();
    }
}
