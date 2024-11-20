package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class PurchaseOperation implements OperationHandler {

    @Override
    public void handle(FruitTransaction transaction, Map<String, Integer> inventory) {
        String fruitName = transaction.getFruit();
        int currentQuantity = inventory.get(fruitName);
        inventory.put(fruitName, currentQuantity - transaction.getQuantity());
    }
}
