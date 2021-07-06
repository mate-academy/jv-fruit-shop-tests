package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public int perform(Transaction transferObject) {
        Storage.getStorage().put(new Fruit(transferObject.getName()), transferObject.getQuantity());
        return transferObject.getQuantity();
    }
}
