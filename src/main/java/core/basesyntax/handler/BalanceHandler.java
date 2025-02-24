package core.basesyntax.handler;

import core.basesyntax.database.Storage;
import core.basesyntax.transactor.FruitTransaction;

public class BalanceHandler implements OperationHandler {

    @Override
    public void operate(FruitTransaction fruitTransaction) {
        Storage.storage.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
