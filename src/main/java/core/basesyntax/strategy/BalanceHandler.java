package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;

import core.basesyntax.storage.FruitTransaction;

public class BalanceHandler implements OperationHandler {
    @Override
    public void handleTransaction(FruitTransaction transaction) {
        if (transaction.getFruit() == null) {
            throw new IllegalArgumentException("Item name can't be NULL");
        }
        if (transaction.getOperation() != FruitTransaction.Operation.BALANCE) {
            throw new IllegalArgumentException("There can be no other type of transaction"
                                                + " in a balance transaction");
        }
        if (transaction.getQuantity() <= 0) {
            throw new RuntimeException("Balance can't be less than one, your balance: "
                    + transaction.getQuantity());
        }
        fruitStorage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
