package core.basesyntax.operators;

import core.basesyntax.db.Storage;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void execute(String product, int amount) {
        if (Storage.storage.containsKey(product)) {
            int productAmount = Storage.storage.get(product) + amount;
            Storage.storage.replace(product, productAmount);
        } else {
            Storage.storage.put(product, amount);
        }
    }
}
