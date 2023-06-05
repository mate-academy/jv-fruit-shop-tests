package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.NullFruitNameException;
import core.basesyntax.models.FruitTransaction;

public class BalanceHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getFruit() == null || fruitTransaction.getFruit().length() == 0) {
            throw new NullFruitNameException("Cant add fruit without name to storage");
        }
        Storage.fruitMap.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
