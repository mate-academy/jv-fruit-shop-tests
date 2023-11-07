package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public interface FruitTransactionValidation {
    void validateTransaction(FruitTransaction fruitTransaction);
}
