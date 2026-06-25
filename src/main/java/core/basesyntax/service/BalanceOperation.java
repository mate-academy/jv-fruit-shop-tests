package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity can not be negative " + transaction.getQuantity());
        }
        Storage.setQuantity(transaction.getFruit(), transaction.getQuantity());
    }

}
