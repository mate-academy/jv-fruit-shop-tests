package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandler implements OperationHandler {

    @Override
    public void operate(FruitTransaction transaction) {
        Storage.dataBase.put(transaction.getFruit(), transaction.getQuantity());
    }
}
