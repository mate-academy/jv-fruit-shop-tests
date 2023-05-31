package core.basesyntax.db.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.db.model.FruitTransaction;
import core.basesyntax.db.strategy.OperationHandler;
import java.util.Objects;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getFruit() == null) {
            throw new NullPointerException("Can't add value with null key");
        }
        if (Objects.equals(transaction.getFruit(), "")) {
            throw new RuntimeException("Can't add value with empty key");
        }
        Storage.getAll().put(transaction.getFruit(), transaction.getQuantity());
    }
}
