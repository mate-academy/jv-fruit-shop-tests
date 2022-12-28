package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import core.basesyntax.exception.OperationException;

public class SupplyOperation implements Operational {
    @Override
    public void action(String fruit, int amount) {
        if (!fruitStorage.containsKey(fruit)) {
            throw new OperationException("Unknown fruit name");
        }
        if (amount <= 0) {
            throw new OperationException("Invalid amount " +
                    "(less than or equal to zero)");
        }
        fruitStorage.put(fruit, fruitStorage.get(fruit) + amount);
    }
}
