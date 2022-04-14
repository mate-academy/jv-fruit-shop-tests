package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.ShopOperation;
import core.basesyntax.model.Fruit;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public int apply(ShopOperation shopOperation) {
        int fruitAmount = shopOperation.getQuantity();
        Storage.storage.put(new Fruit(shopOperation.getFruitName()), fruitAmount);
        return fruitAmount;
    }
}
