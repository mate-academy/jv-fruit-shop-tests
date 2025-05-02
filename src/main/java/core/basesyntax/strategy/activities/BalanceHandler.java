package core.basesyntax.strategy.activities;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.Objects;

public class BalanceHandler implements OperationHandler {
    private static final int PRIMARY_QUANTITY = 0;
    private static final Storage storage = new StorageImpl();

    @Override
    public void executeTransaction(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        if (fruit == null) {
            throw new RuntimeException("Fruit is null.");
        }
        int quantity = transaction.getQuantity();

        Integer currentValue = storage.getValue(fruit);
        storage.setValue(fruit,
                quantity + Objects.requireNonNullElse(currentValue, PRIMARY_QUANTITY));
    }
}
