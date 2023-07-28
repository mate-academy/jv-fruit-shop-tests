package core.basesyntax.strategy.handlers;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    private static final String EXCEPTION
            = "Each fruit should have balance operation as first operation type in input file";
    private static final String LESS_THAN_ZERO_EXCEPTION
            = "Quantity cannot be negative! Insert correct values to input file";
    private static final String INVALID_INPUT_PARAMETER
            = "Invalid input parameter in handle()";

    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException(INVALID_INPUT_PARAMETER);
        }
        if (FruitStorage.FRUITS.isEmpty()) {
            throw new RuntimeException(EXCEPTION);
        }
        int quantity = FruitStorage.FRUITS.get(transaction.getFruit());
        if ((quantity - transaction.getQuantity()) < 0) {
            throw new RuntimeException(LESS_THAN_ZERO_EXCEPTION);
        }
        FruitStorage.FRUITS.put(transaction.getFruit(), quantity - transaction.getQuantity());
    }
}
