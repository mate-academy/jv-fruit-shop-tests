package core.basesyntax.service.impl;

import core.basesyntax.FruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionValidation;

public class FruitTransactionValidationImpl implements FruitTransactionValidation {
    @Override
    public void validateTransaction(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new FruitTransactionException("Transaction cannot be null");
        }
        if (fruitTransaction.getFruit() == null) {
            throw new FruitTransactionException("Fruit name cannot be set as null");
        }
        if (fruitTransaction.getQuantity() == null) {
            throw new FruitTransactionException("Fruit quantity cannot be set as null");
        }
        if (fruitTransaction.getQuantity() < 0) {
            throw new FruitTransactionException("Fruit quantity cannot be negative value");
        }

    }
}
