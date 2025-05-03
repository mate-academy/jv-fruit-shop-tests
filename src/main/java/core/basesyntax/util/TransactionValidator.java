package core.basesyntax.util;

import core.basesyntax.model.FruitTransaction;

public class TransactionValidator {

    public void validate(FruitTransaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if (transaction.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive number, but was: "
                + transaction.getQuantity());
        }
    }
}
