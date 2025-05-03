package strategy.impl;

import db.Storage;
import java.util.Map;
import model.FruitTransaction;
import model.Operation;
import strategy.OperationHandler;

public class SupplyOperation implements OperationHandler {
    @Override
    public Map.Entry<String, Integer> apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("The quantity for Supply Operation can't be negative = "
                    + transaction.getQuantity());
        }
        if (transaction.getOperation() != Operation.SUPPLY) {
            throw new RuntimeException("The operation must be SUPPLY, but it is "
                    + transaction.getOperation());
        }
        Storage.reports.put(transaction.getFruit(),
                getBalance(transaction) + transaction.getQuantity());
        return Map.entry(transaction.getFruit(), getBalance(transaction));
    }

    private int getBalance(FruitTransaction transaction) {
        return Storage.reports.get(transaction.getFruit()) == null
                ? 0 : Storage.reports.get(transaction.getFruit());
    }
}
