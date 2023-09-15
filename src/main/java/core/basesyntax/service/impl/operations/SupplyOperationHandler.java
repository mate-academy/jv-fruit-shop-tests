package core.basesyntax.service.impl.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.service.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void performOperation(String fruit, int amount) {
        Storage.addFruits(fruit, amount);
    }
}
