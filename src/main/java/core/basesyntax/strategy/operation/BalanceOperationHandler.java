package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionException;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    private static final String EXCEPTION_MESSAGE_FOR_FRUIT = "Fruit can't be null";
    private static final String EXCEPTION_MESSAGE_FOR_QUANTITY
            = "Quantity can't be negative or zero: ";

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getFruit() == null) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_FRUIT);
        }
        if (fruitTransaction.getQuantity() <= 0) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_QUANTITY
                    + fruitTransaction.getQuantity());
        }
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
