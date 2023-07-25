package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {

    @Override
    public int apply(FruitTransaction fruitTransaction) {
        int existingQuantity = Storage.fruits.getOrDefault(fruitTransaction.getFruit(), 0);
        int newQuantity = existingQuantity - fruitTransaction.getQuantity();
        if (newQuantity < 0) {
            return existingQuantity;
        } else {
            Storage.fruits.put(fruitTransaction.getFruit(), newQuantity);
            return newQuantity;
        }
    }
}
