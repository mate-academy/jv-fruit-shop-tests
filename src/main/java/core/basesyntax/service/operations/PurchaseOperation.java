package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exceptions.InvalidFruitException;
import core.basesyntax.service.exceptions.InvalidOperationException;
import core.basesyntax.service.exceptions.InvalidResultException;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getOperation() != FruitTransaction.Operation.PURCHASE) {
            throw new InvalidOperationException("Invalid operation. "
                    + "Should be: 'PURCHASE', but was: "
                    + transaction.getOperation() + ".");
        }
        if (!Storage.containsKey(transaction.getFruit())) {
            Storage.put(transaction.getFruit(), transaction.getQuantity());
            throw new InvalidFruitException("Fruit: " + transaction.getFruit()
                    + " wasn't found in the storage");
        }
        int newValue = Storage.getQuantity(transaction.getFruit())
                - transaction.getQuantity();
        if (newValue < 0) {
            throw new InvalidResultException("Result: "
                    + newValue + " for item: "
                    + transaction.getFruit()
                    + " can't be negative number");
        }
        if (Storage.containsKey(transaction.getFruit())) {
            Storage.put(transaction.getFruit(), newValue);
        }
    }
}
