package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionException;
import core.basesyntax.strategy.OperationHandler;
import java.util.Optional;

public class SupplyOperationHandler implements OperationHandler {
    private static final int INITIAL_BALANCE = 0;
    private static final String EXCEPTION_MESSAGE_FOR_NEGATIVE_VALUE
            = "Invalid data. Quantity can't be negative: ";
    private static final String EXCEPTION_MESSAGE_FOR_NULL_VALUE
            = "Invalid data. Fruit can't be null";

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (Storage.fruits.get(fruitTransaction.getFruit()) == null) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_NULL_VALUE);
        }
        if (Storage.fruits.get(fruitTransaction.getFruit()) < 0) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_NEGATIVE_VALUE
                    + Storage.fruits.get(fruitTransaction.getFruit()));
        }
        Integer oldSum = Optional.ofNullable(Storage.fruits.get(fruitTransaction.getFruit()))
                .orElse(INITIAL_BALANCE + fruitTransaction.getQuantity());
        Storage.fruits.put(fruitTransaction.getFruit(), oldSum + fruitTransaction.getQuantity());
    }
}
