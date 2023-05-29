package core.basesyntax.transactionhandler;

import core.basesyntax.service.FruitTransaction;

public interface TransactionHandler {
    void operate(FruitTransaction fruitTransaction);
}
