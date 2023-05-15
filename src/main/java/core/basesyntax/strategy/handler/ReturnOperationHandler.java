package core.basesyntax.strategy.handler;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        Integer amount = FruitStorage.storage.getOrDefault(transaction.getFruit(), 0);
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Invalid return data of fruit: " + transaction.getFruit());
        }
        FruitStorage.storage.put(transaction.getFruit(), amount + transaction.getQuantity());
    }
}
