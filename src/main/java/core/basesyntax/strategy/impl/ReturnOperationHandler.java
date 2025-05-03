package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Optional;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int quantity = Optional.ofNullable(Storage.fruits.get(transaction.getFruit())).orElse(0);
        if (quantity != 0) {
            Storage.fruits.put(transaction.getFruit(), transaction.getQuantity() + quantity);
        } else {
            Storage.fruits.put(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
