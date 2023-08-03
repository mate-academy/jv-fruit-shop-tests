package core.basesyntax.handlers;

import core.basesyntax.storage.Storage;
import core.basesyntax.verificator.NegativeVerifier;

public class SupplyHandler implements OperationHandler {
    @Override
    public void handle(String fruit, int quantity) {
        NegativeVerifier.isNegative(quantity);
        Storage.storage.merge(fruit, quantity, Integer::sum);
    }
}
