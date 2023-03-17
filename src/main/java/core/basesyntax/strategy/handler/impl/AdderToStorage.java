package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public final class AdderToStorage {
    public static void add(FruitTransaction transaction) {
        int currentQuantity = Storage.storage
                .getOrDefault(transaction.getFruit(), 0);
        int transactionQuantity = transaction.getQuantity();
        if (transactionQuantity < 0) {
            throw new RuntimeException("You can`t add to storage negative value");
        }
        Storage.storage.put(transaction.getFruit(),
                currentQuantity + transactionQuantity);
    }
}
