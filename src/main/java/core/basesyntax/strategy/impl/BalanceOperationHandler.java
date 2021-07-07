package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public int apply(Transaction transactionDto) {
        int currentQuantity = transactionDto.getQuantity();
        Storage.storage.put(new Fruit(transactionDto.getName()), currentQuantity);
        return currentQuantity;
    }
}
