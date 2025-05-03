package core.basesyntax.strategy;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction, ShopStorage storage) {
        storage.updateQuantity(transaction.getFruit(), transaction.getQuantity());
    }
}
