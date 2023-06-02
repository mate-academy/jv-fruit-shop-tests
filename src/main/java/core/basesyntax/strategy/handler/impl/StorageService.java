package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class StorageService {
    public static void add(FruitTransaction transaction) {
        int currentQuantity = Storage.FRUITS.getOrDefault(transaction.getFruit(), 0);
        int transactionQuantity = transaction.getQuantity();
        if (transactionQuantity < 0) {
            throw new RuntimeException("You can`t add to storage negative value");
        }
        Storage.FRUITS.put(transaction.getFruit(), currentQuantity + transaction.getQuantity());
    }
}
