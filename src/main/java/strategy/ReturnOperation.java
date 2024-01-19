package strategy;

import db.Storage;
import model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void processOperation(FruitTransaction fruitTransaction) {
        if (Storage.fruits.get(fruitTransaction.getFruit()) == null) {
            throw new RuntimeException("U can not return fruit that was never bought");
        }
        int quantity = Storage.fruits.get(fruitTransaction.getFruit());
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity() + quantity);
    }
}
