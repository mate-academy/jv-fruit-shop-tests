package core.basesyntax.transactionexecutor;

import core.basesyntax.db.Storage;
import core.basesyntax.fruittransaction.FruitTransaction;

public class OperationHandlerBalanceImpl implements OperationHandler {

    public void handle(FruitTransaction transaction) {
        Storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
