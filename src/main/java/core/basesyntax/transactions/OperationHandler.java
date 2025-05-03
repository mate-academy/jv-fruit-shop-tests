package core.basesyntax.transactions;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {

    boolean processTransaction(FruitTransaction transaction);
}
