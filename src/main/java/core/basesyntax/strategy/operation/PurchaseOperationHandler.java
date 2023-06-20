package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionException;
import core.basesyntax.strategy.OperationHandler;
import java.util.Optional;

public class PurchaseOperationHandler implements OperationHandler {
    private static final String EXCEPTION_MESSAGE_FOR_NULL_VALUE
            = "Invalid data. Fruit can't be null";
    private static final String EXCEPTION_MESSAGE_FOR_NEGATIVE_VALUE
            = "Invalid data. Quantity can't be negative or zero: ";
    private static final String EXCEPTION_MESSAGE_FOR_INCORRECT_VALUE
            = "Can't sell current fruit. Available quantity is: ";

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        Integer oldSum = Optional.ofNullable(Storage.fruits.get(fruitTransaction.getFruit()))
                .orElseThrow(() -> new TransactionException(EXCEPTION_MESSAGE_FOR_NULL_VALUE));
        if (oldSum <= 0) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_NEGATIVE_VALUE + oldSum);
        } else if (oldSum < fruitTransaction.getQuantity()) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_INCORRECT_VALUE + oldSum);
        }
        Storage.fruits.put(fruitTransaction.getFruit(), oldSum - fruitTransaction.getQuantity());
    }
}
