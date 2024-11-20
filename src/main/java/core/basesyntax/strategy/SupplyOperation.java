package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class SupplyOperation implements OperationHandler {

    @Override
    public void handle(FruitTransaction transaction, Map<String, Integer> inventory) {
        String fruitName = transaction.getFruit();
        int transactionQuantity = transaction.getQuantity();
        inventory.merge(fruitName, transactionQuantity, Integer::sum);
    }
}
