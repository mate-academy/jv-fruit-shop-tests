package strategy.impl;

import db.Storage;
import java.util.Map;
import model.FruitTransaction;
import model.Operation;
import strategy.OperationHandler;

public class BalanceOperation implements OperationHandler {
    @Override
    public Map.Entry<String, Integer> apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Balance can't be negative, in file balance = "
                    + transaction.getQuantity());
        }
        if (transaction.getOperation() != Operation.BALANCE) {
            throw new RuntimeException("The operation must be BALANCE, but it is "
                    + transaction.getOperation());
        }
        Storage.reports.put(transaction.getFruit(), transaction.getQuantity());
        return Map.entry(transaction.getFruit(), transaction.getQuantity());
    }
}
