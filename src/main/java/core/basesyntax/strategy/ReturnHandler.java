package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;

import core.basesyntax.storage.FruitTransaction;

public class ReturnHandler implements OperationHandler {
    @Override
    public void handleTransaction(FruitTransaction transaction) {
        if (transaction.getFruit() == null) {
            throw new IllegalArgumentException("The name cannot be null");
        }
        if (transaction.getQuantity() <= 0) {
            throw new IllegalArgumentException("Return cannot be less than or equal to 0");
        }
        if (transaction.getOperation() != FruitTransaction.Operation.RETURN) {
            throw new IllegalArgumentException("You cannot use other"
                    + " operations in the return handler");
        }
        fruitStorage.merge(transaction.getFruit(), transaction.getQuantity(), Integer::sum);
    }
}
