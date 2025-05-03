package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class ReturnHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("FruitTransaction can't be null");
        }
        String fruitName = fruitTransaction.getFruit();
        int newQuantity = fruitTransaction.getQuantity();
        if (Storage.fruits.containsKey(fruitName)) {
            int oldQuantity = Storage.fruits.get(fruitName);
            Storage.fruits.put(fruitName, oldQuantity + newQuantity);
        } else {
            Storage.fruits.put(fruitName, newQuantity);
        }
    }
}
