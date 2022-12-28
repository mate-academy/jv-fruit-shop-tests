package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import core.basesyntax.exception.OperationException;

public class PurchaseOperation implements Operational {
    @Override
    public void action(String fruit, int amount) {
        if (fruit == null) {
            throw new OperationException("Null input element");
        }
        if (!fruitStorage.containsKey(fruit)) {
            throw new OperationException("Unknown fruit name");
        }
        if (amount <= 0) {
            throw new OperationException("Invalid amount of fruits");
        }
        if (amount > fruitStorage.get(fruit)) {
            throw new OperationException("Sold more than what is in the storage");
        }
        fruitStorage.put(fruit, fruitStorage.get(fruit) - amount);
    }
}
