package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction transaction) {
        if (Storage.fruits.containsKey(transaction.getFruit())) {
            if (Storage.fruits.get(transaction.getFruit()) < transaction.getQuantity()) {
                throw new RuntimeException(String.format("Not enough %s in storage. "
                                + "Requested: %d, Available: %d",
                        transaction.getFruit(),
                        transaction.getQuantity(),
                        Storage.fruits.get(transaction.getFruit())));
            }
            Storage.fruits.put(transaction.getFruit(),
                    Storage.fruits.get(transaction.getFruit()) - transaction.getQuantity());
        } else {
            throw new RuntimeException(String.format("%s not found in storage.",
                    transaction.getFruit()));
        }
    }
}
