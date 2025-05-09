package fruitshop.strategy;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getFruit() == null) {
            throw new NullPointerException("Fruit can't be null");
        }
        Storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
