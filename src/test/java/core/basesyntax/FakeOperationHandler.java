package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class FakeOperationHandler implements OperationHandler {

    private boolean wasCalled = false;
    private FruitTransaction transaction = null;

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        wasCalled = true;
        transaction = fruitTransaction;
    }

    public FruitTransaction getTransaction() {
        return transaction;
    }

    public boolean isWasCalled() {
        return wasCalled;
    }
}
