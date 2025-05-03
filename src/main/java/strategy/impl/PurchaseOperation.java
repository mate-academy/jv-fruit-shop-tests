package strategy.impl;

import db.Storage;
import java.util.Map;
import model.FruitTransaction;
import model.Operation;
import strategy.OperationHandler;

public class PurchaseOperation implements OperationHandler {
    @Override
    public Map.Entry<String, Integer> apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("The quantity for Purchase Operation can't be negative = "
                    + transaction.getQuantity());
        }
        if (transaction.getOperation() != Operation.PURCHASE) {
            throw new RuntimeException("The operation must be PURCHASE, but it is "
                    + transaction.getOperation());
        }
        int finalBalance = getBalance(transaction) - transaction.getQuantity();
        if (finalBalance < 0) {
            throw new RuntimeException("It's not possible make purchase = "
                    + transaction.getQuantity()
                    + ", due insufficient balance = " + getBalance(transaction));
        }
        Storage.reports.put(transaction.getFruit(),
                getBalance(transaction) - transaction.getQuantity());
        return Map.entry(transaction.getFruit(), getBalance(transaction));
    }

    private int getBalance(FruitTransaction transaction) {
        return Storage.reports.get(transaction.getFruit()) == null
                ? 0 : Storage.reports.get(transaction.getFruit());
    }
}
