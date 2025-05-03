package core.basesyntax.handler;

import core.basesyntax.database.Storage;
import core.basesyntax.transactor.FruitTransaction;

public class ReturnHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction transaction) {
        int amountAfterReturn = Storage.storage.get(transaction.getFruit())
                + transaction.getQuantity();
        Storage.storage.put(transaction.getFruit(), amountAfterReturn);
    }
}
