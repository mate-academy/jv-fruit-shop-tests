package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(TransactionDto transactionDto) {
        Fruit fruit = new Fruit(transactionDto.getFruitName());
        if (!Storage.storage.containsKey(fruit)) {
            Storage.storage.put(fruit, transactionDto.getQuantity());
        }
    }
}
