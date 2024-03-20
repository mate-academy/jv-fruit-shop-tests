package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void operationProcess(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() <= 0) {
            throw new RuntimeException("Can't return nothing");
        }
        int quantity = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        int newQuantity = quantity + fruitTransaction.getQuantity();
        Storage.fruitsStorage.put(fruitTransaction.getFruit(), newQuantity);
    }

}
