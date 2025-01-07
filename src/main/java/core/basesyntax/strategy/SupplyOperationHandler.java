package core.basesyntax.strategy;

import core.basesyntax.storage.Storage;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        int balance = 0;
        if (Storage.getFruits().containsKey(fruit)) {
            balance = Storage.getFruits().get(fruit);
        }
        Storage.getFruits().put(fruit, balance + quantity);
    }
}
