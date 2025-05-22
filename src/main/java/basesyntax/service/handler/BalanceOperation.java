package basesyntax.service.handler;

import basesyntax.model.FruitTransaction;
import basesyntax.storage.Storage;

public class BalanceOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        Storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
