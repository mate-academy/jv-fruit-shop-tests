package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class BalanceHandler implements FruitOperationHandler {
    @Override
    public void executeOperation(FruitTransaction transaction, Map<String, Integer> inventory) {
        String fruit = transaction.getFruit();
        int newQuantity = transaction.getQuantity();

        if (newQuantity < 0) {
            throw new RuntimeException("Cannot complete balance operation: "
                   + "resulting quantity cannot be negative for " + fruit);
        }

        inventory.put(fruit, newQuantity);
    }
}
