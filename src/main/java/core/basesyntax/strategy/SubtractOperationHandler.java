package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.ShopOperation;
import core.basesyntax.model.Fruit;

public class SubtractOperationHandler implements OperationHandler {
    @Override
    public int apply(ShopOperation shopOperation) {
        Fruit fruit = new Fruit(shopOperation.getFruitName());
        int oldAmount = Storage.storage.getOrDefault(fruit, 0);
        if (shopOperation.getOperation().equals("p") && shopOperation.getQuantity() > oldAmount) {
            throw new RuntimeException("Not enough product");
        }
        int newAmount = oldAmount - shopOperation.getQuantity();
        Storage.storage.put(fruit, newAmount);
        return newAmount;
    }
}
