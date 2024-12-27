package core.basesyntax.strategy;

import core.basesyntax.db.Storage;

public class SupplyOperation implements OperationHandler {

    public void handle(FruitTransaction transaction) {
        OperationHandler.validateQuantity(transaction.getQuantity());
        Storage.add(transaction.getFruit(), transaction.getQuantity());
    }
}
