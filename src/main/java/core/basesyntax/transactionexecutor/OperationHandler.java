package core.basesyntax.transactionexecutor;

import core.basesyntax.fruittransaction.FruitTransaction;

public interface OperationHandler {
    void handle(FruitTransaction transaction);
}
