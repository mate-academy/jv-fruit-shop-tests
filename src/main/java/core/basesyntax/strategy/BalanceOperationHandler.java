package core.basesyntax.strategy;

import java.util.Map;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(Map<String, Integer> inventory, String fruit, int quantity) {
        System.out.println("Current balance of " + fruit + ": " + inventory.getOrDefault(fruit, 0));
    }
}
