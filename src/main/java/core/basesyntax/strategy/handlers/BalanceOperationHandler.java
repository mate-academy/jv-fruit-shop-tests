package core.basesyntax.strategy.handlers;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    private static final String INVALID_INPUT_PARAMETER
            = "Invalid input parameter in handle()";

    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException(INVALID_INPUT_PARAMETER);
        }
        FruitStorage.FRUITS.put(transaction.getFruit(), transaction.getQuantity());
    }
}
