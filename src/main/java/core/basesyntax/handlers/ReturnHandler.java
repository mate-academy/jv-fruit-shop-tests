package core.basesyntax.handlers;

import core.basesyntax.storage.Storage;
import core.basesyntax.verificator.NegativeVerifier;

public class ReturnHandler implements OperationHandler {
    @Override
    public void handle(String fruit, int quantity) {
        NegativeVerifier.isNegative(quantity);
        if (Storage.storage.get(fruit) == null) {
            throw new RuntimeException("You can`t return this fruit: " + fruit);
        }
        Storage.storage.merge(fruit, quantity, Integer::sum);
    }
}
