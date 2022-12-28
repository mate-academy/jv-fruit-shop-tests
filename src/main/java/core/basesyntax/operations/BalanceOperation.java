package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;

import core.basesyntax.exception.OperationException;

public class BalanceOperation implements Operational {
    @Override
    public void action(String fruit, int amount) {
        if (fruit == null) {
            throw new OperationException("Null input element");
        }
        if (amount < 0) {
            throw new OperationException("Invalid amount of fruits");
        }
        if (!fruit.matches("[a-z]+")) {
            throw new OperationException("Invalid name of fruits");
        }
        fruitStorage.put(fruit, amount);
    }
}
