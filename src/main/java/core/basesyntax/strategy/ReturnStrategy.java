package core.basesyntax.strategy;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;

public class ReturnStrategy implements OperationHandler {
    @Override
    public void doActivity(FruitTransaction transaction, ShopStorage fruitStorage) {
        int currentQuantity = fruitStorage.getQuantity(transaction.getFruit());
        int newQuantity = currentQuantity + transaction.getQuantity();
        fruitStorage.updateQuantity(transaction.getFruit(), newQuantity);
    }
}
