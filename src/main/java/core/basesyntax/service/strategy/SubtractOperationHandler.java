package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;

public class SubtractOperationHandler implements OperationHandler {

    @Override
    public void execute(Transaction transaction) {
        Fruit fruit = new Fruit(transaction.getFruit().getName());
        int oldAmount = Storage.storage.getOrDefault(fruit, 0);
        if (transaction.getQuantity() > oldAmount) {
            throw new RuntimeException("Not enough product");
        }
        int newAmount = oldAmount - transaction.getQuantity();
        Storage.storage.put(fruit, newAmount);
    }
}
