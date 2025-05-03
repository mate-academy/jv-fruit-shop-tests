package core.basesyntax.strategy;

import core.basesyntax.db.Storage;

public class BalanceOperation implements OperationHandler {
    public void handle(FruitTransaction transaction) {
        OperationHandler.validateQuantity(transaction.getQuantity());
        Storage.update(transaction.getFruit(), transaction.getQuantity());
    }
}
