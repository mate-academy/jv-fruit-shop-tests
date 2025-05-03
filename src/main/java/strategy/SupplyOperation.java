package strategy;

import db.Storage;
import model.FruitTransaction;

public class SupplyOperation implements Operating {
    public SupplyOperation() {
    }

    @Override
    public void doOperation(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        Integer transactionAmount = fruitTransaction.getQuantity();
        Integer storageAmount = Storage.getFruitsNumber(fruit);
        Storage.storeFruit(fruit, storageAmount + transactionAmount);
    }
}
