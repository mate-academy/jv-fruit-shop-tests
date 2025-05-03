package core.basesyntax.strategy.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (Storage.FRUITS.get(fruitTransaction.getFruit()) == null) { //added after approval
            throw new RuntimeException("Can't make operation " + fruitTransaction.getOperation()
                    + " with " + fruitTransaction.getFruit() + ", there's no information "
                    + "about start balance");
        }
        int prevQuantity = Storage.FRUITS.get(fruitTransaction.getFruit());
        Storage.FRUITS.put(fruitTransaction.getFruit(), prevQuantity
                + fruitTransaction.getQuantity());
    }
}
