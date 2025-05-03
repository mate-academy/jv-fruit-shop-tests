package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.ShopOperation;
import core.basesyntax.model.Fruit;

public class AddOperationHandler implements OperationHandler {

    @Override
    public void apply(ShopOperation shopOperation) {
        Fruit fruit = new Fruit(shopOperation.getFruitName());
        int oldAmount = Storage.storage.getOrDefault(fruit, 0);
        int newAmount = oldAmount + shopOperation.getQuantity();
        Storage.storage.put(fruit, newAmount);
    }
}
