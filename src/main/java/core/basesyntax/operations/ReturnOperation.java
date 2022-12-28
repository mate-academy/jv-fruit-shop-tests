package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import core.basesyntax.exception.OperationException;

public class ReturnOperation implements Operational {
    @Override
    public void action(String fruit, int amount) {
        if (amount <= 0) {
            throw new OperationException("Invalid amount" +
                    "(less than or equal to zero)");
        }
        if (fruit == null) {
            throw new OperationException("Null name of fruits");
        }
        if (!fruitStorage.containsKey(fruit)) {
            throw new OperationException("Unknown fruit name");
        }
        fruitStorage.put(fruit, fruitStorage.get(fruit) + amount);
    }
}
