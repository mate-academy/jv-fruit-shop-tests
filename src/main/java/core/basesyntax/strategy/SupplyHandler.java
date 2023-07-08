package core.basesyntax.strategy;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;

public class SupplyHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction, ShopStorage storage) {
        int currentQuantity = storage.getQuantity(transaction.getFruit());
        int newQuantity = currentQuantity + transaction.getQuantity();
        storage.updateQuantity(transaction.getFruit(), newQuantity);
    }
}
