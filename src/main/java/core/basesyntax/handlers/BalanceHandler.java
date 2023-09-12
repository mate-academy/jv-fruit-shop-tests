package core.basesyntax.handlers;

import core.basesyntax.storage.Storage;
import core.basesyntax.verificator.NegativeVerifier;

public class BalanceHandler implements OperationHandler {
    @Override
    public void handle(String fruit, int quantity) {
        NegativeVerifier.isNegative(quantity);
        Storage.storage.put(fruit, quantity);
    }
}
