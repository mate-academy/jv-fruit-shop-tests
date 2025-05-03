package strategy;

import db.Storage;
import model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void processOperation(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int quantity = Storage.fruits.get(fruit);
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("U can not supply negative amount of fruits");
        }
        if (quantity > 0) {
            Storage.fruits.put(fruit, quantity + fruitTransaction.getQuantity());
        } else {
            Storage.fruits.put(fruit, fruitTransaction.getQuantity());
        }
    }
}
