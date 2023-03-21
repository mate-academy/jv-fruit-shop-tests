package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitTransaction;

public class BalanceOperationStrategy implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getOperation() == null
                || transaction.getFruit() == null
                || transaction.getFruit().equals("")) {
            throw new RuntimeException("Wrong input data. "
                    + "Fields of FruitTransactions can't be null or empty");
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Wrong input data. "
                    + "Balance operation quantity can't be negative");
        }
        Storage.getFruitStorage().put(transaction.getFruit(), transaction.getQuantity());
    }
}
